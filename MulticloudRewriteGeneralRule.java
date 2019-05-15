import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelWriter;
import org.apache.calcite.rel.core.RelFactories;
import org.apache.calcite.rel.externalize.RelWriterImpl;
import org.apache.calcite.tools.RelBuilderFactory;

import java.io.PrintWriter;

public class MulticloudRewriteGeneralRule extends RelOptRule {

	public static final MulticloudRewriteGeneralRule INSTANCE = new MulticloudRewriteGeneralRule(RelFactories.LOGICAL_BUILDER);


	public MulticloudRewriteGeneralRule(RelBuilderFactory relBuilderFactory) {
		super(operand(RelNode.class, any()),
				relBuilderFactory,
				MulticloudRewriteGeneralRule.class.getSimpleName());
	}


	public void onMatch(RelOptRuleCall call) {
		System.out.println();
		System.out.println(this.toString());
		RelWriter rw = new RelWriterImpl(new PrintWriter(System.out, true));
		call.rels[0].explain(rw);
	}
}
