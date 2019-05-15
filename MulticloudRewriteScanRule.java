import org.apache.calcite.adapter.jdbc.JdbcTableScan;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rel.core.RelFactories;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RelBuilderFactory;

import java.util.List;

public class MulticloudRewriteScanRule extends RelOptRule {

	public static final MulticloudRewriteScanRule INSTANCE = new MulticloudRewriteScanRule(RelFactories.LOGICAL_BUILDER);


	public MulticloudRewriteScanRule(RelBuilderFactory relBuilderFactory) {
		super(operand(JdbcTableScan.class, none()),
				relBuilderFactory,
				MulticloudRewriteScanRule.class.getSimpleName());
	}


	public void onMatch(RelOptRuleCall call) {
		//System.out.println();
		//System.out.println(this.toString());
		//RelWriter rw = new RelWriterImpl(new PrintWriter(System.out, true));
		//call.rels[0].explain(rw);

		JdbcTableScan scan = (JdbcTableScan) call.rels[0];

		RelOptTable table = scan.getTable();
		String schemaName = table.getQualifiedName().get(0);
		String tableName = table.getQualifiedName().get(1);

		SchemaPlus originSchema = ((MulticloudConvention) scan.getConvention()).getOriginSchema();
		String originSchemaName = originSchema.getName();
		List<SchemaPlus> fragmentSchemas = ((MulticloudConvention) scan.getConvention()).getFragmentSchemas();

		// transform the original scan only
		// fragment schema scans introduced as a result of original scan transformation have to be left intact
		if (schemaName.equals(originSchemaName)) {
			RelBuilder builder = relBuilderFactory.create(scan.getCluster(), table.getRelOptSchema());
			RelNode multiCloudScan = builder
					.scan("sqlrewriter_frag1", "medinfo1")
					.project(builder.field("multiid"), builder.field("id"), builder.field("firstname"), builder.field("lastname"))
					.scan("sqlrewriter_frag2", "medinfo2")
					.project(builder.field("multiid"), builder.field("age"))
					.join(JoinRelType.INNER, "multiid")
					.scan("sqlrewriter_frag3", "medinfo3")
					.project(builder.field("multiid"), builder.field("illness"))
					.join(JoinRelType.INNER, "multiid")
					.project(builder.field("id"), builder.field("firstname"), builder.field("lastname"), builder.field("age"), builder.field("illness"))
					.build();

			//System.out.println();
			//multiCloudScan.explain(rw);

			call.transformTo(multiCloudScan);
		}
	}
}
