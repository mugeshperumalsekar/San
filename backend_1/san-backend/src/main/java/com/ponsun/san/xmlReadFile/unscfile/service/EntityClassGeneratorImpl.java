package com.ponsun.san.xmlReadFile.unscfile.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class EntityClassGeneratorImpl implements EntityClassGenerator{

    public ResponseEntity<String> generateEntityClass(String className, String tableName, List<String> columns) {
        StringBuilder classContent = new StringBuilder();

        // Package declaration (modify if needed)
        classContent.append("package com.ponsun.san.unscfile.entity;\n\n");

        // Import statements
        classContent.append("import jakarta.persistence.*;\n");
        classContent.append("import lombok.Data;\n");
        classContent.append("import lombok.experimental.Accessors;\n");
        classContent.append("import java.io.Serializable;\n\n");

        // Class annotations and definition
        classContent.append("@Data\n");
        classContent.append("@Entity\n");
        classContent.append("@Accessors(chain = true)\n");
        classContent.append("@Table(name = \"").append(tableName).append("\")\n");
        classContent.append("public class ").append(className).append(" implements Serializable {\n\n");
        classContent.append("    private static final long serialVersionUID = 1L;\n\n");

        // Primary Key
        classContent.append("    @Id\n");
        classContent.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
        classContent.append("    @Column(name = \"id\", nullable = false)\n");
        classContent.append("    private Integer id;\n\n");

        for (String column : columns) {
            String snakeCaseColumn = toSnakeCase(column);
            String camelCaseField = toCamelCase(column);

            classContent.append("    @Column(name = \"").append(snakeCaseColumn).append("\")\n");
            classContent.append("    private String ").append(camelCaseField).append(";\n\n");
        }

        classContent.append("}\n");
        String entityPath = "src\\main\\java\\com\\json\\read\\unscfile\\entity";
        String currentDir = System.getProperty("user.dir") + File.separator + entityPath;

        // Create directory if it does not exist
        File directory = new File(currentDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn’t exist
        }
        File file = new File(directory, className + ".java");
        try (FileWriter writer =  new FileWriter(file)) {
            writer.write(classContent.toString());
            return ResponseEntity.ok("Entity class " + className + " generated successfully!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error writing file: " + e.getMessage());
        }
    }

    public static String toSnakeCase(String columnName) {
        // Convert special characters to underscores
        String snakeCase = columnName.replaceAll("[^a-zA-Z0-9]", "_").toLowerCase();

        // Ensure it does not start with a number
        if (Character.isDigit(snakeCase.charAt(0))) {
            snakeCase = "_" + snakeCase;
        }

        return snakeCase;
    }

    public static String toCamelCase(String columnName) {
        // Convert to snake_case first
        String snakeCase = toSnakeCase(columnName);

        // Convert snake_case to camelCase
        StringBuilder camelCase = new StringBuilder();
        boolean toUpper = false;
        for (char c : snakeCase.toCharArray()) {
            if (c == '_') {
                toUpper = true;
            } else {
                camelCase.append(toUpper ? Character.toUpperCase(c) : c);
                toUpper = false;
            }
        }

        return camelCase.toString();
    }

}
