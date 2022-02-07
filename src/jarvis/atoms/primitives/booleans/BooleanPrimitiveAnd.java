package jarvis.atoms.primitives.booleans;

import java.util.ArrayList;

import jarvis.atoms.AbstractAtom;
import jarvis.atoms.BoolAtom;
import jarvis.atoms.ObjectAtom;
import jarvis.interpreter.JarvisInterpreter;

public class BooleanPrimitiveAnd extends BooleanPrimitiveOperation {
	
	//OPERATIONSPRIMITIVES
	/*
	* Cette classe implante la partie de l'opération primitive spécifique au '&&' appelée : ET ou AND logique.
	* 
	*/
	
	@Override
	public String makeKey() {
		return "BooleanPrimitiveAnd";
	}

	@Override
	protected AbstractAtom calculateResult(JarvisInterpreter ji, BoolAtom bool1, BoolAtom bool2) {
		// C'est ici que l'opération réelle a lieu
		boolean result = bool1.getValue() && bool2.getValue();
		
		// Ici, construit un objet bool manuellement
		// À noter, on retourne un objet de type bool, pas un atome de type
		// bool.
		ArrayList<AbstractAtom> data = new ArrayList<AbstractAtom>();
		data.add(new BoolAtom(result));
		
		return new ObjectAtom(((ObjectAtom) ji.getEnvironment().get("bool")), data, ji);
	}	
}
