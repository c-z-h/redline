package knowledgeGraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;

@WebServlet("/GraphServlet")
public class GraphServlet extends HttpServlet{
	
	public static final String NS = "http://www.semanticweb.org/administrator/ontologies/2018/3/untitled-ontology-30#";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String keyword = req.getParameter("entity");
		if ("".equals(keyword)||keyword == null) keyword = "redline";
		String path=req.getSession().getServletContext().getRealPath("")+"redline.owl";
		String json = getJson(getOntClass(keyword, path));
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(json);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
	public String getJson(OntClass ontology) {
		JSONObject ans=new JSONObject();	
		JSONObject element=new JSONObject();
		if (ontology == null) 
			return "{}";
		Iterator<OntClass> itr1 = ontology.listSubClasses(true);
		Iterator<OntClass> itr2 = ontology.listSuperClasses(true);
		JSONArray nodes = new JSONArray();
		JSONArray edges = new JSONArray();
		JSONObject ont = new JSONObject();
		ont.put("id",ontology.getLocalName());
		ont.put("name", ontology.getLocalName());
		ont.put("shortname", ontology.getComment(null));
		ont.put("category", 1);
		ont.put("href", "");
		nodes.put(ont);
		while (itr1.hasNext()) {
			OntClass temp = itr1.next();
			JSONObject json1 = new JSONObject();
			JSONObject json2 = new JSONObject();
			json1.put("id",temp.getLocalName());
			json1.put("name", temp.getLocalName());
			json1.put("shortname", temp.getComment(null));
			String a = temp.getComment(null);
			json1.put("category", 0);
			json1.put("href", "");
			json2.put("source", ontology.getLocalName());
			json2.put("atrribute","派生");
			json2.put("target", temp.getLocalName());
			nodes.put(json1);
			edges.put(json2);
		}
		while (itr2.hasNext()) {
			OntClass temp = itr2.next();
			JSONObject json1 = new JSONObject();
			JSONObject json2 = new JSONObject();
			json1.put("id",temp.getLocalName());
			json1.put("name", temp.getLocalName());
			json1.put("shortname", temp.getComment(null));
			json1.put("category", 0);
			json1.put("href", "");
			json2.put("source",temp.getLocalName());
			json2.put("atrribute","派生");
			json2.put("target",ontology.getLocalName());
			nodes.put(json1);
			edges.put(json2);
		}
		element.put("nodes", nodes);
		element.put("edges",edges);
		ans.put("elements", element);
		return ans.toString();
	}
	
	public OntClass getOntClass(String keyword, String path) {
		OntModel model = ModelFactory.createOntologyModel();
		File file=new File(path);
		FileInputStream f1 = null;
		OntClass ans = null;
		try {
			f1 = new FileInputStream(file);
			RDFReader d = model.getReader();
			d.read(model, f1, "");
	        ans = model.getOntClass(NS+keyword);	   
	        f1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ans;
	}
}
