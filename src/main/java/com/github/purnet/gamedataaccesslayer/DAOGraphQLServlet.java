package com.github.purnet.gamedataaccesslayer;

import graphql.GraphQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DAOGraphQLServlet extends HttpServlet {
	
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	//System.out.println("In the GET request");
		AppSchemaSingleton appSchema = AppSchemaSingleton.getInstance();
		String requestID = UUID.randomUUID().toString();
		System.out.println("requestID " + requestID);
        String a = ThreadId.get();
		System.out.println("a" + a);

		try {
		    Thread.sleep(5000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		String b = ThreadId.get();
		System.out.println("b" + b);
		String s = "Hello World. Get is not yet implemented" + b;
		resp.getOutputStream().write(s.getBytes());
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//System.out.println("In the POST request");

		AppSchemaSingleton appSchema = AppSchemaSingleton.getInstance();
		BufferedReader br = req.getReader();
		String line = null;
		String query = "";
		String jsonString = "";
		try {
			while ((line = br.readLine()) != null) {
				query = query.concat(line);
			}
		}
		catch(Exception e){
	         e.printStackTrace();
	    }
//		System.out.println("body is:");
//		System.out.println(query);
		ObjectMapper mapper = new ObjectMapper();
		Query q = new Query();
		try {
	        q = mapper.readValue(query, Query.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		query = q.getQuery();
//        System.out.println("query is:");
//        System.out.println(query);
        Object result = null;
        TypeReference<Map<String,Object>> typeRef = new TypeReference<Map<String,Object>>() {};
        
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.getSession(ThreadId.get()).beginTransaction();  
        if (q.getVariables() == null || q.getVariables().equals("")) { 
        	result = new GraphQL(appSchema.getSchema()).execute(query).getData();
        	System.out.println("query plain no args");
        } else {
        	Map<String, Object> variables = mapper.readValue(q.getVariables(), typeRef);
        	result = new GraphQL(appSchema.getSchema()).execute(query, new Object(), variables).getData();
        	System.out.println("query  args");
        }
        System.out.println("to this point");
        sessionManager.getSession(ThreadId.get()).getTransaction().commit();   
        QueryResult qr = new QueryResult(result);

        jsonString = mapper.writeValueAsString(qr);
//        System.out.println("query result is:");
//        System.out.println(jsonString);
        
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Content-Type", "application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(jsonString);
        resp.getWriter().flush();
        resp.getWriter().close();
        
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
		resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Origin", "*");

	}
}
