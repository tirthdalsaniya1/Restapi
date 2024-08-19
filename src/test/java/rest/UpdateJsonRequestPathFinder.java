package rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateJsonRequestPathFinder {

	private static final ObjectMapper mapper = new ObjectMapper();

    public static List<String> findPathsForElement(String json, String elementName) throws IOException {
        JsonNode rootNode = mapper.readTree(json);
        List<String> paths = new ArrayList<String>();
        findPathsForElement(rootNode, elementName, paths, "");
        return paths;
    }
    
    private static void findPathsForElement(JsonNode node, String elementName, List<String> paths, String currentPath) {
        if (node.isObject()) {
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode childNode = node.get(fieldName);
                if (fieldName.equals(elementName)) {
                    paths.add(currentPath.isEmpty() ? fieldName : currentPath + "/" + fieldName);
                }
                findPathsForElement(childNode, elementName, paths, currentPath.isEmpty() ? fieldName : currentPath + "/" + fieldName);
            }
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                findPathsForElement(node.get(i), elementName, paths, currentPath + "[" + i + "]");
            }
        }
    }

   

    public static void main(String[] args) throws IOException {
        String json = "{ \"name\": \"John Doe\", \"age\": 30, \"address\": { \"street\": \"123 Main St\", \"city\": \"Anytown\", \"zipcode\": \"12345\", \"coordinates\": { \"latitude\": 40.7128, \"longitude\": -74.0060 } }, \"emails\": [ \"john.doe@example.com\", \"jdoe@example.com\" ], \"children\": [ { \"name\": \"Alice\", \"age\": 5, \"hobbies\": [ \"Drawing\", \"Reading\" ] }, { \"name\": \"Bob\", \"age\": 8, \"hobbies\": [ \"Cycling\", \"Swimming\" ] } ], \"spouse\": { \"name\": \"Jane Doe\", \"age\": 28, \"occupation\": \"Teacher\", \"contacts\": [ { \"type\": \"mobile\", \"number\": \"123-456-7890\" }, { \"type\": \"email\", \"address\": \"jane.doe@example.com\" } ] }, \"pets\": [ { \"name\": \"Fluffy\", \"type\": \"Cat\" }, { \"name\": \"Rover\", \"type\": \"Dog\" } ] }";
        List<String> paths = findPathsForElement(json, "contacts");
        for (String path : paths) {
            System.out.println("Path to 'pets': " + path);
        }
    }
}
