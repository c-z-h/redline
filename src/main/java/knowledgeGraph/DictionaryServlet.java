package knowledgeGraph;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;

@WebServlet("/DictionaryServlet")
public class DictionaryServlet extends HttpServlet{

	public static final String NS = "http://www.semanticweb.org/administrator/ontologies/2018/3/untitled-ontology-30#";
	
	public DictionaryServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getSession().getServletContext().getRealPath("")+"redline.owl";
		OntClass ontology = getOntClass("redline", path);
		req.setAttribute("entityList", getHierarchy(ontology));
		req.getRequestDispatcher("knowledgeDictionary.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	public List<Entity> getHierarchy(OntClass ontology) {
		List<Entity> list = new ArrayList<>();
		if (ontology == null) 
			return list;
		list.add(new Entity(ontology.getLocalName(),"",ontology.getComment(null)));
		Set<OntClass> directSub = new HashSet<>();
		Set<OntClass> directSuper = new HashSet<>();
		Iterator<OntClass> itr3 = ontology.listSubClasses(true);
		Iterator<OntClass> itr4 = ontology.listSuperClasses(true);
		while (itr3.hasNext()) {
			OntClass temp = itr3.next();
			directSub.add(temp);
		}
		while (itr4.hasNext()) {
			OntClass temp = itr4.next();
			directSuper.add(temp);
		}
		Iterator<OntClass> itr1 = ontology.listSubClasses();
		Iterator<OntClass> itr2 = ontology.listSuperClasses();
		while (itr1.hasNext()) {
			OntClass temp = itr1.next();
			if (!directSub.contains(temp)) {
				list.add(new Entity(temp.getLocalName(),"<span style='color:gray'>子类</span>",temp.getComment(null)));
			} else {
				list.add(new Entity(temp.getLocalName(),"<span style='color:gray'>直接子类</span>",temp.getComment(null)));
			}
		}
		while (itr2.hasNext()) {
			OntClass temp = itr2.next();
			if (!directSuper.contains(temp))
				list.add(new Entity(temp.getLocalName(),"<span style='color:lightgreen'>父类</span>",temp.getComment(null)));
			else
				list.add(new Entity(temp.getLocalName(),"<span style='color:lightgreen'>直接父类</span>",temp.getComment(null)));
		}
		return list;
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
	
	
	
//		public static void main(String[] args) {
//			OntModel model = ModelFactory.createOntologyModel();
//			File file=new File("C:\\Users\\Administrator\\Desktop\\redline.owl");
//			FileInputStream f1 = null;
//			OntClass ans = null;
//			try {
//				f1 = new FileInputStream(file);
//				RDFReader d = model.getReader();
//				d.read(model, f1, "");
//		        ans = model.getOntClass("http://www.semanticweb.org/administrator/ontologies/2018/3/untitled-ontology-30#redline");	   
//		        f1.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			System.out.println(ans.getComment(null));
//		}
		


}
