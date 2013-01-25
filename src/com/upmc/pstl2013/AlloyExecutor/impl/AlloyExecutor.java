package com.upmc.pstl2013.alloyExecutor.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.JetException;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.ErrorWarning;
import edu.mit.csail.sdg.alloy4compiler.ast.Command;
import edu.mit.csail.sdg.alloy4compiler.ast.Module;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig.Field;
import edu.mit.csail.sdg.alloy4compiler.parser.CompUtil;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Options;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;
import edu.mit.csail.sdg.alloy4compiler.translator.TranslateAlloyToKodkod;
import edu.mit.csail.sdg.alloy4viz.VizGUI;

/**
 * Cette classe se charge d'éxécuter les fichiers Alloy généré précédement 
 *
 */
public class AlloyExecutor implements IAlloyExecutor 
{
	
	private IAlloyGenerator generator;
	private static Logger log = Logger.getLogger(AlloyExecutor.class);
	private List<String> results;
	
	/**
	 * Constructeur
	 */
	public AlloyExecutor(IAlloyGenerator generator)
	{
		super();
		this.generator = generator;
		this.results = new ArrayList<String>();
	}
	
	@Override
	public String executeFiles() throws Err, JetException
	{
		//Résultat
		StringBuilder resultat = new StringBuilder();
		// on lance la génération des fichiers Alloy
		generator.generateFile();
		
		// The visualizer (We will initialize it to nonnull when we visualize an Alloy solution)
		VizGUI viz = null;

		String filename;

		// Alloy4 sends diagnostic messages and progress reports to the A4Reporter.
		// By default, the A4Reporter ignores all these events (but you can extend the A4Reporter to display the event for the user)
		A4Reporter rep = new A4Reporter() {
			// For example, here we choose to display each "warning" by printing it to System.out
			@Override public void warning(ErrorWarning msg) {
				System.out.print("Relevance Warning:\n"+(msg.toString().trim())+"\n\n");
				System.out.flush();
			}
		};

		for (File file : generator.getGeneratedFiles()) 
		{
			try {
				filename = file.getCanonicalPath();
				//TODO enlever
				filename = "D:\\INFORMATIQUE\\JAVA\\workspaces\\workspacePSTL\\pstl-2013\\model\\alloy\\GEN-simple.als";

				//Vérifie que le fichier soit de type ALLOY
				if (filename.substring(filename.length()-3, filename.length()).equals("als"))
				{
					// Parse+typecheck the model
					resultat.append("=========== Parsing+Typechecking "+filename+" =============\n");
					
					Module world = CompUtil.parseEverything_fromFile(rep, null, filename);

					// Choose some default options for how you want to execute the commands
					A4Options options = new A4Options();
					options.solver = A4Options.SatSolver.SAT4J; //TODO: minisatx5 JNI

					for (Command command: world.getAllCommands()) {
						// Execute the command
						resultat.append("============ Command "+command+": ============\n");
						A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), command, options);
						// Print the outcome
						//resultat.append(ans.toString());
						
						//TODO
						getResults(ans);
						
						// If satisfiable...
						if (ans.satisfiable()) {
							// You can query "ans" to find out the values of each set or type.
							// This can be useful for debugging.
							// You can also write the outcome to an XML file
							ans.writeXML("alloy_example_output.xml");
							// You can then visualize the XML file by calling this:
							if (viz==null) {
								viz = new VizGUI(false, "alloy_example_output.xml", null);
							} else {
								viz.loadXML("alloy_example_output.xml", true);
							}
						}
					}
				}
			} catch (IOException e) {
				resultat.append("Impossible de récupérer le chemin du fichier : " + e.toString() + "\n");
				log.error("Impossible de récupérer le chemin du fichier : " + e.toString(),e);
			}
		}
		return resultat.toString();
	}

	@Override
	public void reset() {
		generator.reset();
	}

	@Override
	public void getResults(A4Solution ans) 
	{
		
		StringBuilder sb = new StringBuilder();
		for (Sig sig : ans.getAllReachableSigs())
		{
			sb.append(sig.label).append("=").append(ans.eval(sig)).append("\n");
            for(Field f:sig.getFields()) sb.append(sig.label).append("<:").append(f.label).append("=").append(ans.eval(f)).append("\n");
		}
		/*if (!solved) return "---OUTCOME---\nUnknown.\n";
        if (eval == null) return "---OUTCOME---\nUnsatisfiable.\n";
        String answer = toStringCache;
		if (answer != null) return answer;
        Instance sol = eval.instance();
        
        sb.append("---INSTANCE---\n" + "integers={");
        boolean firstTuple = true;
        for(IndexedEntry<TupleSet> e:sol.intTuples()) {
            if (firstTuple) firstTuple=false; else sb.append(", ");
            // No need to print e.index() since we've ensured the Int atom's String representation is always equal to ""+e.index()
            Object atom = e.value().iterator().next().atom(0);
            sb.append(atom2name(atom));
        }
        sb.append("}\n");
        try {
            for(Sig s:sigs) {
                sb.append(s.label).append("=").append(eval(s)).append("\n");
                for(Field f:s.getFields()) sb.append(s.label).append("<:").append(f.label).append("=").append(eval(f)).append("\n");
            }
            for(ExprVar v:skolems) {
                sb.append("skolem ").append(v.label).append("=").append(eval(v)).append("\n");
            }
            //return toStringCache = sb.toString();
        } catch(Err er) {
            //return toStringCache = ("<Evaluator error occurred: "+er+">");
        }	
        */	
	}
	
	
	/** Dumps the Kodkod solution into String. 
    @Override 
    public String toString() {
        
    }*/
}
