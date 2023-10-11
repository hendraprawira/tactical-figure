
/*
 * Copyright PT Len Industri (Persero)
 *
 * THIS SOFTWARE SOURCE CODE AND ANY EXECUTABLE DERIVED THEREOF ARE PROPRIETARY
 * TO PT LEN INDUSTRI (PERSERO), AS APPLICABLE, AND SHALL NOT BE USED IN ANY WAY
 * OTHER THAN BEFOREHAND AGREED ON BY PT LEN INDUSTRI (PERSERO), NOR BE REPRODUCED
 * OR DISCLOSED TO THIRD PARTIES WITHOUT PRIOR WRITTEN AUTHORIZATION BY
 * PT LEN INDUSTRI (PERSERO), AS APPLICABLE.
 */

/*
 * @author Hendra
 * */

/*
 * the class for Websocket,
 */


import javax.websocket.*;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/* set Endpoint
 */
@ServerEndpoint(value = "/v1/tactic-point-ws")
public class PointWS {
    /* set onOpen to add client/connection
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println ("Connected, sessionID = " + session.getId());
        SESSIONS.add(session);
        System.out.println(SESSIONS);
    }

    /* onMessage websocket
     */
    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println(message);
        if (message.equals("quit")) {
            try {
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Bye!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }


    /* onClose websocket
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() +
                " closed because " + closeReason);
        SESSIONS.remove(session);
    }

    /* Create a function to send messages to all connected clients
     */
    public static void sendMessageToAll(String message) {
                for (Session session : SESSIONS) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        // Handle exceptions, such as closed sessions
                        System.err.println(e);
                        e.printStackTrace();
                    }
                }
    }

    /* set list session for multiple client/conection
     */
    private static Set<Session> SESSIONS = new HashSet<>();
}
