package jarvis.atoms.primitives.booleans;

import java.util.ArrayList;

import jarvis.atoms.AbstractAtom;
import jarvis.atoms.BoolAtom;
import jarvis.atoms.ObjectAtom;
import jarvis.interpreter.JarvisInterpreter;

public class BooleanPrimitiveNot extends BooleanPrimitiveOperation {
	
	//OPERATIONSPRIMITIVES
	/*
	* Cette classe implante la partie de l'opération primitive spécifique au '!' appelée : NOT ou Négation logique.
	* 
	*/
	
	@Override
	public String makeKey() {
		return "BooleanPrimitiveNot";
	}
	
	// Le nombre d'argument doit etre 0 (seulement self)
    protected void init() {
        argCount = 0;
    }

	// Comme la négation prend seulement un argument et la methode execute de la classe BooleanPrimitiveOperation 
	// attends recevoir un deuxième argument, il est nécessaire de réecrire la methode 'execute' pour quelle aie 
	// seulement un argument afin de calculer le résultat de la négation.
	
	@Override
	protected AbstractAtom execute(JarvisInterpreter ji,ObjectAtom self) {
		
		//Ici, on peut assumer que l'objet qui a reçu le message (self) est un int et possède donc
		//le champ "value". 
		BoolAtom bool1 = (BoolAtom) self.message("bool");
		
		//Procède finalement au calcul spécifique à chaque primitive concernant 1 bool.	
		return calculateResult(ji, bool1);
	}
	
	protected AbstractAtom calculateResult(JarvisInterpreter ji, BoolAtom bool1) {
		// C'est ici que l'opération réelle a lieu
		boolean result = !bool1.getValue();
		
		// Ici, construit un objet bool manuellement
		// À noter, on retourne un objet de type bool, pas un atome de type
		// bool.
		ArrayList<AbstractAtom> data = new ArrayList<AbstractAtom>();
		data.add(new BoolAtom(result));
		
		return new ObjectAtom(((ObjectAtom) ji.getEnvironment().get("bool")), data, ji);
	}
}
