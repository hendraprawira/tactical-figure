import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.TimeoutException;

import TacticalPoint.TacticPoint;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.tyrus.server.Server;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.*;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicQos;

import javax.websocket.DeploymentException;

public class Main {

    public static void main(String[] args) throws IOException, DeploymentException {
        DbConnect app = new DbConnect();
        Connection conn = app.connect();
        //OSPL Pub
        boolean autodispose_unregistered_instances = false;
        System.setProperty(
                ServiceEnvironment.IMPLEMENTATION_CLASS_NAME_PROPERTY,
                "org.opensplice.dds.core.OsplServiceEnvironment");
        ServiceEnvironment env = ServiceEnvironment
                .createInstance(Main.class.getClassLoader());

        DomainParticipantFactory dpf = DomainParticipantFactory
                .getInstance(env);

        DomainParticipant p = dpf.createParticipant(0);
        PolicyFactory policyFactory = env.getSPI().getPolicyFactory();

        Reliability r = PolicyFactory.getPolicyFactory(env).Reliability()
                .withReliable();
        Durability d = PolicyFactory.getPolicyFactory(env).Durability()
                .withPersistent();

        TopicQos topicQos = p.getDefaultTopicQos().withPolicies(r, d);
        Collection<Class<? extends Status>> statuses = new HashSet<Class<? extends Status>>();
        Topic<TacticPoint> topic = p.createTopic("TacticPoint", TacticPoint.class, topicQos, null , statuses);

        Partition partition = PolicyFactory.getPolicyFactory(env).Partition()
                .withName("TacticPoint");

        PublisherQos pubQos = p.getDefaultPublisherQos().withPolicy(partition);
        Publisher pub = p.createPublisher(pubQos);

        WriterDataLifecycle wdlq = policyFactory.WriterDataLifecycle()
                .withAutDisposeUnregisteredInstances(autodispose_unregistered_instances);
        DataWriterQos dwQos = pub.copyFromTopicQos(pub.getDefaultDataWriterQos().withPolicy(wdlq),topic.getQos());

        DataWriter<TacticPoint> writer = pub.createDataWriter(topic,dwQos);
        TacticPoint tacticMsg = new TacticPoint();
        try {
            writer.registerInstance(tacticMsg);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


        // Create an HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);

        PointHandler pointHandler = new PointHandler(writer, p, env, conn);
        SingleHandler singleHandler = new SingleHandler(writer, p, env, conn);
        MultiHandler multiHandler = new MultiHandler(writer, p, env, conn);
        server.createContext("/v1/tactic-point", pointHandler);
        server.createContext("/v1/tactic-single", singleHandler);
        server.createContext("/v1/tactic-multi", multiHandler);

        OsplSubs osplSubs = new OsplSubs();
        osplSubs.start();

        Server serverWS;
        serverWS = new Server("0.0.0.0", 8083,
                "", PointWS.class, SingleWS.class, MultiWS.class);
        // Start the server
        serverWS.start();
        server.start();
    }

}
