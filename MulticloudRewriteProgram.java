import org.apache.calcite.plan.RelOptLattice;
import org.apache.calcite.plan.RelOptMaterialization;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.tools.Program;
import org.apache.calcite.tools.Programs;
import org.apache.calcite.tools.RuleSet;
import org.apache.calcite.tools.RuleSets;
import org.apache.calcite.util.Holder;

import java.util.List;
import java.util.function.Consumer;

public class MulticloudRewriteProgram implements Program {

	public static MulticloudRewriteProgram INSTANCE = new MulticloudRewriteProgram();

	private MulticloudRewriteProgram() {
		super();
	}

	@Override
	public RelNode run(RelOptPlanner planner,
					   RelNode relNode,
					   RelTraitSet traits,
					   List<RelOptMaterialization> materializations,
					   List<RelOptLattice> lattices) {

		RuleSet multiCloudRewriteRules = RuleSets.ofList(
				MulticloudRewriteGeneralRule.INSTANCE,
				MulticloudRewriteScanRule.INSTANCE
		);
		Program hep = Programs.hep(multiCloudRewriteRules, false, null);
		return hep.run(planner, relNode, traits, materializations, lattices);
	}


	public static Consumer<Holder<Program>> prepend(Program program) {
		return (holder) -> {
			if (holder == null) {
				throw new IllegalStateException("No program holder");
			}
			holder.set(Programs.sequence(program, Programs.standard()));
		};
	}
}
