package src.ToyORB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;


public class ProxyGenerator {
    private static String name;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        name = args[0];
        createFile();
    }


    public static void createFile() throws IOException, ClassNotFoundException {
        BufferedWriter output;
        String path = "src/src/Proxies/" + name + "ClientProxy.java";
        File file = new File(path);
        file.getParentFile().mkdirs();

        output = new BufferedWriter(new FileWriter(file));

        output.write(String.format("""
                package src.Proxies;
                                
                import src.ToyORB.Commons.ConcreteAddress;
                import src.ToyORB.MessageMarshaller.ResponseMessage;
                import src.ServerInterfaces.%sServer;
                                
                import java.io.Serializable;
               
                public class %sClientProxy extends GenericClientProxy implements %sServer {                  
                """, name, name, name)
        );

        output.write("""
            public %sClientProxy(ConcreteAddress serverAddress){
                super(serverAddress);
            }
       
        """.formatted(name));

        String className = "src.ServerImplementations." + name + "Server";

        Class<?> c = Class.forName(className);

        Method[] declaredMethods = c.getDeclaredMethods();

        for (Method m : declaredMethods) {
            int modifiers = m.getModifiers();
            String modifierString = Modifier.toString(modifiers);
            String methodName = m.getName();
            String method_type = m.getReturnType().getSimpleName();

            output.write(String.format("""
                                @Override
                                %s %s %s(
                            """, modifierString, method_type, methodName));


            Parameter[] parameters = m.getParameters();
            StringBuilder parametersForSerializable = new StringBuilder();


            if (parameters.length != 0) {
                output.write("\t\t");
                for (int i = 0; i < parameters.length; i++) {
                    String parameterName = parameters[i].getName();
                    parametersForSerializable.append(parameterName);
                    String type = parameters[i].getType().getSimpleName();

                    output.write(type + " " + parameterName);

                    if (i + 1 < parameters.length) {
                        output.write(", ");
                        parametersForSerializable.append(", ");
                    }
                }
            }
            output.write("\n\t) {");
            output.write("""
                        
                        ResponseMessage serverResponse = makeRequestToServer("%s", new Serializable[] {%s});
                        return (%s) serverResponse.response;
                    }
                
                """.formatted(methodName, parametersForSerializable.toString(), method_type));

        }
        output.write("}\n");

        System.out.println("Created client side proxy file on path '" + path + "' for " + name + " app");
        output.close();
    }
}