import java.util.*;

/*
 *  1. JSON is a document format used to encode information that is both human-readable
 *  and machine-readable. JSON format is explained at http://json.org. Please write a
 *  JSON parser that accepts an input JSON string and produces a Map output structure.
 *  Do not use any existing library to do the parsing.
 *
 *  Example input JSON:
 *  {
 *      "debug" : "on",
 *      "window" : {
 *      "title" : "sample",
 *      "size": 500
 *      }
 *  }
 *
 *  The corresponding output for the input JSON should be:
 *
 *  Map<String, Object> output = JsonParser.parse(input);
 *  assert output.get("debug").equals("on");
 *  assert (Map<String, Object>(output.get("window")).get("title").equals("sample")
 *  assert (Map<String, Object>(output.get("window")).get("size").equals(500)
 */
public class JSONParser
{
    public static int index; // Index of current char in JSON string
    public static int lengthOfJSON; // Length of JSON string to reduce time overhead
    public static String state; // Represents the current state of the parsing program
    public static final String DEFAULT_KEY = "DEFAULT"; // Default key when input JSON string is an array

    /*
     *  Final constants for the possible state of the JSONParser.
     */

    private static final String KEY   = "KEY";
    private static final String VALUE = "VALUE";
    private static final String END   = "END";

    /**
     * Parses through a JSON-formatted string and returns a Map
     * representation of the given input string.
     *
     * @param json A JSON string (MUST be in a valid JSON format)
     * @return     A Map representation of the given JSON string
     */
    public static Map<String,Object> parse(String json)
    {
        /*  [OPTIONAL]: Removes all whitespace from the input JSON string.
         *
         *  (1) With library functions
         *  json = json.replaceAll("\\s", "");
         *
         *  (2) Without library functions
         *  StringBuilder sb = new StringBuilder();
         *  for(char c: json.toCharArray()) {
         *      if(c != ' ') sb.append(c);
         *  }
         *  json = sb.toString();
         */

        index = 0;
        lengthOfJSON = json.length();
        Map<String,Object> output = new HashMap<>();

        // Handles some valid minimal JSON strings
        if(json.equals("") || json.equals("{}") || json.equals("[]"))
        {
            return output;
        }

        if(json.charAt(index) == '{')
        {
            state = KEY;
            index++;
            output = parseObject(json);
        }
        else if(json.charAt(index) == '[')
        {
            state = KEY;
            index++;
            output.put(DEFAULT_KEY, parseArray(json));
        }
        else
        {
            output.put(DEFAULT_KEY, json);
        }

        return output;
    }

    /**
     * Returns all (key : value) pairs within a single JSON object
     *
     * @param json A JSON string (MUST be in a valid JSON format)
     * @return     A Map representation of the JSON object
     */
    private static Map<String, Object> parseObject(String json)
    {
        String key = null;
        Map<String, Object> map = new HashMap<>();

        loop:
        while(index < lengthOfJSON)
        {
            char currentChar = json.charAt(index);
            switch(state)
            {
                case KEY:
                    key = getString(json);
                    state = VALUE;
                    break;
                case VALUE:
                    switch(currentChar)
                    {
                        case '"':
                            map.put(key, getString(json));
                            key = null; // Resets key to ensure no overwriting occurs
                            break;
                        case '{':
                            state = KEY;
                            index++;
                            map.put(key, parseObject(json)); // Recursively call this method on a new object
                            break;
                        case '[':
                            index++;
                            map.put(DEFAULT_KEY, parseArray(json)); // Associates incoming array with a default key
                            break;
                        case ',':
                            state = KEY;
                            break;
                        case '}':
                            state = END;
                            break;
                        default: // Value could be an int
                            if(Character.isDigit(currentChar))
                            {
                                map.put(key, getInt(json));
                            }
                            break;
                    }
                    break;
                case END:
                    break loop;
            }

            index++;
        }

        return map;
    }

    /**
     * Returns all values within a single JSON array
     * @param json A JSON string (MUST be in a valid JSON format)
     * @return     A list representation of the JSON array
     */
    private static List<Object> parseArray(String json)
    {
        List<Object> array = new ArrayList<>();

        loop:
        while(index < lengthOfJSON)
        {
            char currentChar = json.charAt(index);

            switch(currentChar)
            {
                case '"':
                    array.add(getString(json));
                    break;
                case '{':
                    state = KEY;
                    index++;
                    array.add(parseObject(json));
                    break;
                case '[':
                    index++;
                    array.add(parseArray(json));
                    break;
                case ',':
                    break;
                case ']':
                    break loop;
                default: // Value could be an int
                    if(Character.isDigit(currentChar))
                    {
                        array.add(getInt(json));
                    }
                    break;
            }

            index++;
        }

        return array;
    }

    // Parses a string between two double quotes, excluding the double quotes
    private static String getString(String json)
    {
        String temp = json;
        temp = temp.substring(index + 1);
        String string = temp.substring(0, temp.indexOf('\"'));

        // Sets index to the ending double quote char of the parsed string.
        index += string.length() + 1;

        return string;
    }

    // Parses an integer until the first non-digit character is reached
    private static int getInt(String json)
    {
        int startIndex = index;
        int lastIndexOfNum = 0;

        while(index < lengthOfJSON)
        {
            if(!Character.isDigit(json.charAt(index)))
            {
                lastIndexOfNum = index;
                break;
            }
            else
            {
                index++;
            }
        }

        if(lastIndexOfNum - startIndex == 1)
        {
            index--; // Edge case to handle a single-digit integer
        }

        return Integer.parseInt(json.substring(startIndex, lastIndexOfNum));
    }
}
