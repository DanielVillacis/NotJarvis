package jarvis.atoms.primitives.booleans;

import java.util.ArrayList;

import jarvis.atoms.AbstractAtom;
import jarvis.atoms.BoolAtom;
import jarvis.atoms.ObjectAtom;
import jarvis.interpreter.JarvisInterpreter;

public class BooleanPrimitiveOr extends BooleanPrimitiveOperation {
	
	//OPERATIONSPRIMITIVES
	/*
	* Cette classe implante la partie de l'op�ration primitive sp�cifique au '||' appel�e : OU ou OR logique.
	* 
	*/
	
	@Override
	public String makeKey() {
		return "BooleanPrimitiveOr";
	}

	@Override
	protected AbstractAtom calculateResult(JarvisInterpreter ji, BoolAtom bool1, BoolAtom bool2) {
		// C'est ici que l'op�ration r�elle a lieu
		boolean result = bool1.getValue() || bool2.getValue();
		
		// Ici, construit un objet bool manuellement
		// � noter, on retourne un objet de type bool, pas un atome de type
		// bool.
		ArrayList<AbstractAtom> data = new ArrayList<AbstractAtom>();
		data.add(new BoolAtom(result));
		
		return new ObjectAtom(((ObjectAtom) ji.getEnvironment().get("bool")), data, ji);
	}	

}
