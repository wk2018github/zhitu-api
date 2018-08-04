package com.zhuanzhi.structuring;

import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class neo4j implements AutoCloseable {
    private final Driver driver;

    public neo4j(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void printGreeting(final String message) {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    StatementResult result = tx.run("CREATE (a:Greeting) " +
                            "SET a.message = $message " +
                            "RETURN a.message + ', from node ' + id(a)",
                        parameters("message", message));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public static void main(String... args) throws Exception {
        try (neo4j greeter = new neo4j("bolt://localhost:7687", "neo4j", "123456")) {
            greeter.printGreeting("hello, world");
        }
    }
}