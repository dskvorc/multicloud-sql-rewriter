import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

import java.util.Map;

public class MulticloudFragmentSchemaFactory implements SchemaFactory {

	public static final MulticloudFragmentSchemaFactory INSTANCE = new MulticloudFragmentSchemaFactory();


	private MulticloudFragmentSchemaFactory() {
	}


	public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
		MulticloudSchemaParams schemaParams = MulticloudSchemaParams.init(parentSchema, name, operand);
		return new MulticloudFragmentSchema(
				schemaParams.getParentSchema(),
				schemaParams.getName(),
				schemaParams.getDataSource(),
				schemaParams.getDialect(),
				schemaParams.getConvention(),
				schemaParams.getCatalog(),
				schemaParams.getSchema()
		);
	}
}
