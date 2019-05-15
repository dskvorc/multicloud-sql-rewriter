import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.rel.RelWriter;
import org.apache.calcite.rel.core.RelFactories;
import org.apache.calcite.rel.externalize.RelWriterImpl;
import org.apache.calcite.rel.logical.LogicalFilter;
import org.apache.calcite.rel.logical.LogicalProject;
import org.apache.calcite.tools.RelBuilderFactory;

import java.io.PrintWriter;

public class MulticloudRewriteProjectRule extends RelOptRule {

	public static final MulticloudRewriteProjectRule INSTANCE = new MulticloudRewriteProjectRule(RelFactories.LOGICAL_BUILDER);


	public MulticloudRewriteProjectRule(RelBuilderFactory relBuilderFactory) {
		super(operand(LogicalProject.class, operand(LogicalFilter.class, any())),
				relBuilderFactory,
				MulticloudRewriteProjectRule.class.getSimpleName());
	}


	public void onMatch(RelOptRuleCall call) {
		System.out.println();
		System.out.println(this.toString());
		RelWriter rw = new RelWriterImpl(new PrintWriter(System.out, true));
		call.rels[0].explain(rw);
	}
}
