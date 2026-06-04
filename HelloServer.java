import com.HelloWorld;
import com.HelloWorldHelper;
import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;


public class HelloServer {
    public static void main(String[] args) {

        ORB orb = ORB.init(args, null);

        try {

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

            rootpoa.the_POAManager().activate();

            HelloWorldImpl hello = new HelloWorldImpl();

            org.omg.CORBA.Object obj = rootpoa.servant_to_reference(hello);

            HelloWorld href = HelloWorldHelper.narrow(obj);

            org.omg.CORBA.Object obj2 = orb.resolve_initial_references("NameService");

            NamingContextExt ncRef = NamingContextExtHelper.narrow(obj2);

            NameComponent path[] = ncRef.to_name("Hello");

            ncRef.rebind(path, href);
            System.out.println("CORBA Server ready");
            orb.run();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }
}
