public class node {
	node prev,next,child,parent;
	String key;
	int value;
	int degree;
	boolean childcut;
	
	node(int value,String key){
		this.parent=null;
		this.prev=null;
		this.next=null;
		this.degree=0;
		this.key=key;
		this.value=value;
		this.childcut=false;
		
		}
}




