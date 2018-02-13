import java.io.*;
public class Serialization {
    public static boolean write(Object data, String fileLocation) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileLocation);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            out.flush();
        } catch (Exception e) {
            System.out.println("Serialization Write Error!: " + e);
            return false;
        }
        return true;
    }

    public static Object read(String fileLocation) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileLocation));
            Object obj = (Object) input.readObject();
            input.close();
            return obj;
        } catch (FileNotFoundException e) {
            //System.out.println("No file found at " + fileLocation);
            return null;
        } catch (Exception e) {
            System.out.println("Serialization Read Error!: " + e);

        }
        return null;
    }
}
