package jarvis.atoms;

import jarvis.interpreter.JarvisInterpreter;

import java.util.ArrayList;

/*
 * Cette classe implante l'objet de base.
 * L'interpr�teur comprend un objet comme
 * une simple liste de valeurs.
 * L'organisation de ses donn�es est sp�cifi�e
 * par la classe. Celle-ci peut �tre retrouv�e
 * via le lien classReference.
 */
public class ObjectAtom extends AbstractAtom {

	/*
	 * Si vous ajoutez des champs � JarvisClass
	 * ces constantes doivent le refl�ter.
	 * Elles sont utilis�es pour retrouver
	 * les membres d'une classe.
	 * 
	 */
	public static final int ATTRIBUTE_FIELD =0;
	public static final int METHOD_FIELD =1;
	public static final int SUPER_FIELD = 2;
	
	/*
	 * R�f�rence � la classe de cet objet.
	 */
	private ObjectAtom classReference;
	private ArrayList<AbstractAtom> values;
	
	//R�f�rence utile pour faire des reverse lookup
	private JarvisInterpreter ji;

	

	// Constructeur d'objet g�n�rique
	// Utilis� comme raccourci par les fonctions tricheuses.
	public ObjectAtom(ObjectAtom theClass, ArrayList<AbstractAtom> vals,JarvisInterpreter ji) {

		classReference = theClass;

		values = new ArrayList<AbstractAtom>();
		values.addAll(vals);
		
		this.ji=ji;
	}
	
	@Override
	public AbstractAtom interpretNoPut(JarvisInterpreter ji) {	
		return this;
	}

	public ObjectAtom getJarvisClass() {
		return classReference;
	}
	
	
	//Cas sp�cial o� le selecteur n'est pas encore encapsul� dans un atome
	//Support� pour all�ger la syntaxe.
	public AbstractAtom message(String selector) {
		
		return message(new StringAtom(selector));
		
	}
	
    //H�RITAGE
	//VARIABLESCLASSE
	/*
	 * Algorithme de gestion des messages.
	 * Ce bout de code a pour responsabilit� de d�terminer si le message
	 * concerne un attribut ou une m�thode. 
	 * Pour implanter l'h�ritage, cet algorithme doit n�cessairement �tre modifi�.
	 */	
	public AbstractAtom message(AbstractAtom selector) {
		
		
		//Va chercher les attributs
		ListAtom members = classReference.getAllAttributes();

		//V�rifie si c'est un attribut 
		int pos = members.find(selector);
		
		
		if (pos != -1) {
			//C'est un attribut.
			return values.get(pos);
		}
		else {	
			AbstractAtom res = classReference.findMethod(selector);
			return res;
		}
	}
	

	private ListAtom getAllAttributes() {
		ListAtom attributs = (ListAtom)values.get(ATTRIBUTE_FIELD);
		AbstractAtom parent = values.get(SUPER_FIELD);
		 
		ListAtom listeAttributs = new ListAtom();
		
		if(!(parent instanceof AbstractAtom)) {
			listeAttributs = ((ObjectAtom) parent).getAllAttributes();
		}
		
		for(int i = 0; i < attributs.size(); i++) {
			listeAttributs.add(attributs.get(i));
		}
		return listeAttributs;
	}

	
	private AbstractAtom findMethod(AbstractAtom selector) {

		DictionnaryAtom methods = (DictionnaryAtom) values.get(METHOD_FIELD);
		AbstractAtom res = methods.get(selector.makeKey());
		// Super...?
		if(res == null) {
			if(values.get(SUPER_FIELD) instanceof NullAtom) {
				// Rien ne correspond au message
				return new StringAtom("ComprendPas "+ selector);
			}
			else {
				return((ObjectAtom) values.get(SUPER_FIELD)).findMethod(selector);
			}
		}
		return res;
	}
	

	public void setClass(ObjectAtom theClass) {
		classReference = theClass;
	}

	
	
	//Surtout utile pour l'affichage dans ce cas-ci...
	@Override
	public String makeKey() {
		String s="";
		int i=0;
		
		s += "\""+ji.getEnvironment().reverseLookup(classReference)+"\":";
		
		for (AbstractAtom atom : values) {
			
			s+=" "+((ListAtom)classReference.values.get(0)).get(i).makeKey()+":";
			if(atom instanceof ClosureAtom)
			{
				s+=atom;
			}
			else
			{
				s+=atom.makeKey();
			}
			
			i++;
		}
		
		return s;
	}	
	
	public String findClassName(JarvisInterpreter ji){
		
		return ji.getEnvironment().reverseLookup(classReference);
		
	}
	
//	public ArrayList<AbstractAtom> getVals(){
//		return values;
//	}
	
	public void setVal(int valId, AbstractAtom val) {
		values.set(valId, val);
	}

}
