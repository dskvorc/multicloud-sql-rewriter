import org.apache.calcite.schema.SchemaPlus;

import java.util.HashMap;

public class MulticloudSchemaMapper {

	private SchemaPlus rootSchema;
	private MulticloudOriginSchema originSchema;
	private HashMap<String, MulticloudFragmentSchema> fragmentSchemas = new HashMap<>();

	private static MulticloudSchemaMapper INSTANCE;


    public static MulticloudSchemaMapper getMapper() {
        if (INSTANCE == null) {
            return new MulticloudSchemaMapper();
        }
        return INSTANCE;
    }


    public void setRootSchema(SchemaPlus schema) {
        if (rootSchema == null) {
            rootSchema = schema;
        }
    }


    public void setOriginSchema(MulticloudOriginSchema schema) {
        if (originSchema == null) {
            originSchema = schema;
        }
    }


    public void addFragmentSchema(MulticloudFragmentSchema schema) {

    }


    public void addFragmentSchemas(MulticloudFragmentSchema... schemas) {
        for (MulticloudFragmentSchema schema : schemas) {
            addFragmentSchema(schema);
        }
    }
}
