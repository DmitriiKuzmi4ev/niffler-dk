package guru.qa.niffler.jupiter;

import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserQueueExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {

    public static ExtensionContext.Namespace FRIEND_NAMESPACE = ExtensionContext.Namespace.create(UserQueueExtension.class);

    private static Map<User.UserType, Queue<UserJson>> usersQueue = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> userWithFriends = new ConcurrentLinkedQueue<>();
        userWithFriends.add(bindUser("admin", "123"));
        userWithFriends.add(bindUser("admin1", "123"));
        usersQueue.put(User.UserType.FRIEND, userWithFriends);
        Queue<UserJson> userInSent = new ConcurrentLinkedQueue<>();
        userInSent.add(bindUser("admin2", "123"));
        userInSent.add(bindUser("admin4", "123"));
        usersQueue.put(User.UserType.INVITE_SENT, userInSent);
        Queue<UserJson> userInRec = new ConcurrentLinkedQueue<>();
        userInRec.add(bindUser("admin3", "123"));
        userInRec.add(bindUser("admin5", "123"));
        usersQueue.put(User.UserType.INVITE_RECEIVED, userInRec);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Parameter[] parameters = context.getRequiredTestMethod().getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.getType().isAssignableFrom(UserJson.class)) {
                User parameterAnnotation = parameter.getAnnotation(User.class);
                User.UserType userType = parameterAnnotation.userType();
                Queue<UserJson> userQueueByType = usersQueue.get(parameterAnnotation.userType());
                UserJson candidateForTest = null;
                while (candidateForTest == null) {
                    candidateForTest = userQueueByType.poll();
                }
                context.getStore(FRIEND_NAMESPACE).put(context.getUniqueId(),
                        UserJson.builder()
                                .username(candidateForTest.username())
                                .password(candidateForTest.password())
                                .userType(userType)
                                .build());

                break;
            }
        }

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return UserJson.class.isAssignableFrom(parameterContext.getParameter().getType())
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(FRIEND_NAMESPACE)
                .get(extensionContext.getUniqueId(), UserJson.class);
    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        UserJson userFromTest = context.getStore(FRIEND_NAMESPACE).get(context.getUniqueId(), UserJson.class);
        usersQueue.get(userFromTest.userType()).add(userFromTest);
    }

    private static UserJson bindUser(String username, String password) {
        return UserJson.builder()
                .username(username)
                .password(password)
                .build();
    }
}
