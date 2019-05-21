import org.apache.calcite.adapter.jdbc.JdbcTableScan;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rel.core.RelFactories;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RelBuilderFactory;

import java.util.ArrayList;
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
		String multicloudJoinKey = originSchema.unwrap(MulticloudOriginSchema.class).getMulticloudJoinKey();

		/*
		HashMap<String, String> dataFieldMap = new HashMap<>();
		List<String> originTableFields = originSchema.getTable(tableName).getRowType(new JavaTypeFactoryImpl()).getFieldNames();
		for (String originTableField : originTableFields) {
			dataFieldMap.put(originTableField, null);
		}
		for (SchemaPlus fragmentSchema : fragmentSchemas) {
			List<String> fragmentTableFields = fragmentSchema.getTable(tableName).getRowType(new JavaTypeFactoryImpl()).getFieldNames();
			for (String fragmentTableField : fragmentTableFields) {
				if (!fragmentTableField.equals(MULTICLOUD_JOIN_FIELD)) {
					if (dataFieldMap.containsKey(fragmentTableField)) {
						if (dataFieldMap.get(fragmentTableField) == null) {
							dataFieldMap.replace(fragmentTableField, fragmentSchema.getName());
						} else {
							throw new IllegalStateException("Field mapping error: for the field \"" +
									fragmentTableField +
									"\" from fragment schema \"" +
									fragmentSchema.getName() +
									"\" there already exists a mapping in origin schema \"" +
									originSchemaName +
									"\"");
						}
					} else {
						throw new IllegalStateException("Field mapping error: for the field \"" +
								fragmentTableField +
								"\" from fragment schema \"" +
								fragmentSchema.getName() +
								"\" there is no matching field name in origin schema \"" +
								originSchemaName +
								"\"");
					}
				}
			}
		}
		*/

		// transform the original scan only
		// fragment schema scans introduced as a result of original scan transformation have to be left intact
		if (schemaName.equals(originSchemaName)) {
			RelBuilder builder = relBuilderFactory.create(scan.getCluster(), table.getRelOptSchema());

			/*
			RelNode multiCloudScan = builder
					.scan("sqlrewriter_frag1", "medinfo")
					.project(builder.field("multiid"), builder.field("id"), builder.field("firstname"), builder.field("lastname"))
					.scan("sqlrewriter_frag2", "medinfo")
					.project(builder.field("multiid"), builder.field("age"))
					.join(JoinRelType.INNER, "multiid")
					.scan("sqlrewriter_frag3", "medinfo")
					.project(builder.field("multiid"), builder.field("illness"))
					.join(JoinRelType.INNER, "multiid")
					.project(builder.field("id"), builder.field("firstname"), builder.field("lastname"), builder.field("age"), builder.field("illness"))
					.build();
			*/

			boolean skipJoinAtFirstFragment = true;
			for (SchemaPlus fragmentSchema : fragmentSchemas) {
				builder = builder.scan(fragmentSchema.getName(), tableName).project(builder.fields());
				if (!skipJoinAtFirstFragment) {
					builder = builder.join(JoinRelType.INNER, multicloudJoinKey);
				}
				skipJoinAtFirstFragment = false;
			}


			List<String> originTableFieldNames = originSchema.getTable(tableName).getRowType(new JavaTypeFactoryImpl()).getFieldNames();
			ArrayList<RexNode> originTableFields = new ArrayList<>();
			for (String originTableFieldName : originTableFieldNames) {
				RexNode originTableField = builder.field(originTableFieldName);
				originTableFields.add(originTableField);
			}

			builder = builder.project(originTableFields);

			RelNode multiCloudScan = builder.build();
			//System.out.println();
			//multiCloudScan.explain(rw);

			call.transformTo(multiCloudScan);
		}
	}
}
