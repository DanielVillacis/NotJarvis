package jarvis.atoms.primitives;

import jarvis.atoms.AbstractAtom;
import jarvis.atoms.ListAtom;
import jarvis.atoms.ObjectAtom;
import jarvis.atoms.StringAtom;
import jarvis.interpreter.JarvisInterpreter;


public class OperatorSetFunction extends PrimitiveOperationAtom {
	
	// Partie 2.2 : La fonction OperatorSetPrimitive va etre sembable a celle 
	//				de OperatorNewPrimitive. Elle va heriter les methodes init, 
	// 				execute et makeKey de sa classe parent PrimitiveOperationAtom.
	
	/*
	 * Triche pour pouvoir avoir des arguments variables.
	 * Le nombre d'arguments n�cessaire est d�termin� par la taille
	 * de la liste des attributs de la classe qui cr�e l'instance.
	 */
	protected void init() {
		argCount = 2;
	}
	
	//H�RITAGE	
	/*
	 * Operator new. 
	 * Rien de bien myst�rieux: Lorsqu'on fabrique un objet
	 * il suffit de prendre les arguments re�us et de les copier
	 * un � un dans l'objet qu'on fabrique.
	 * Devient plus complexe si on h�rite les membres d'une autre classe.
	 * 
	 */
	@Override
	protected AbstractAtom execute(JarvisInterpreter ji,ObjectAtom self) {	
			
		
		//Seule une classe peut faire new. Ramasser de la classe combien d'attributs �a prend.		
		StringAtom attribut = (StringAtom) ji.getArg();
		
		AbstractAtom val = (AbstractAtom) ji.getArg();
		
		ListAtom parAttr = (ListAtom) self.getJarvisClass().message("attributes");
		int position = parAttr.find(attribut);
		
		
		self.setVal(position,val);
			
		return self;				
	}
	
	@Override
	public String makeKey() {
		return "OperatorSetFunction";
	}

}
