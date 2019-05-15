import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.rel.RelWriter;
import org.apache.calcite.rel.core.RelFactories;
import org.apache.calcite.rel.externalize.RelWriterImpl;
import org.apache.calcite.rel.logical.LogicalFilter;
import org.apache.calcite.tools.RelBuilderFactory;

import java.io.PrintWriter;

public class MulticloudRewriteFilterRule extends RelOptRule {
	public static LogicalFilter storedFilter;

	public static final MulticloudRewriteFilterRule INSTANCE = new MulticloudRewriteFilterRule(RelFactories.LOGICAL_BUILDER);


	public MulticloudRewriteFilterRule(RelBuilderFactory relBuilderFactory) {
		super(operand(LogicalFilter.class, any()),
				relBuilderFactory,
				MulticloudRewriteFilterRule.class.getSimpleName());
	}


	public void onMatch(RelOptRuleCall call) {
		System.out.println();
		System.out.println(this.toString());
		RelWriter rw = new RelWriterImpl(new PrintWriter(System.out, true));
		call.rels[0].explain(rw);
	}
}
