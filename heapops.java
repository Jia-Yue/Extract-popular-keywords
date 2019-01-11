import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

public class heapops {
	
	node maxnode;
	 
	public void insert(node node) {
		//function to insert node in fibonacci heap
		// TODO Auto-generated method stub
		if(maxnode==null){
			 maxnode=node;
			 
		 }
		else if(maxnode!=null){
			 node.prev=maxnode;
			 node.next=maxnode.next;
			 maxnode.next=node;
			 	
			 if(node.next!=null){
			 	node.next.prev=node;
			 }
			 if ( node.next==null)
	           {
	               node.next = maxnode;
	               maxnode.prev = node;
	           }
			 
			 if(maxnode.value<node.value){
			 	maxnode=node;
			 	
			 }	
		
		}

	}//end insert


	public node removemax() {
		// TODO Auto-generated method stub
		//function to remove max node from the fibonacci heap
		 node z=maxnode;
		 
		 if(z!=null){
			 int children=z.degree;
			 node ptr=z.child;
			 node p;
			 
			 //add ptr to root list
			 for(int i=0;i<children;i++){
			
				 p=ptr.next;
				 ptr.prev.next=ptr.next;
				 ptr.next.prev=ptr.prev;		//remove ptr from child list
				 
				 ptr.prev=maxnode;
				 ptr.next=maxnode.next;
				 maxnode.next=ptr;
				 if(ptr.next!=null){
				 	ptr.next.prev=ptr;
				 }
				 ptr.parent=null;
				 ptr=p;						//adding ptr to root list
				 
			 }
			 
			 //remove max element from list
			 z.prev.next=z.next;
			 z.next.prev=z.prev;
			 if(z==z.next){
				 maxnode=null;
			 }
			 else{
				 maxnode=z.next;
				 //calling pairwise combine function after max node is removed
				 consolidate();
			 }			 
			 
		 }
		 
		return z;
		
	}//end function remove max


	private void consolidate() {
		// TODO Auto-generated method stub
		//function to do pair wise combine of the remaining roots
		HashMap<Integer,node> degreetable=new HashMap<Integer,node>();
	    
		//counting the number of elements in the top level list
		int roots=0;
		node x=maxnode;
		if(x!=null){
		 
			while(x.next!=maxnode){
				roots=roots+1;
				x = x.next;
			}
			roots++;
			x=x.next;
		 }
		 
	     
	     
	     //combining all elements in the top level list till same degree roots exist
	     node found=null;
		 while(roots>0){
			int degree=x.degree;
			 node next=x.next;
			 while(true){
					found=degreetable.get(degree);
					if(found==null){
							break;
					}
				 
					//exchanging nodes so that the link of the iteration remains intact
					if(x.value<found.value){
						node temp = found;
	                    found= x;
	                    x = temp;
					}
				 			 
					//making a child of another node
					found.next.prev=found.prev;
					found.prev.next=found.next;
				 
					found.parent=x;
				 
					if(x.child==null){
						x.child=found;
						found.prev=found;
						found.next=found;
					}
				 
					else{
						//insert x in children of found
						found.prev=x.child;
						found.next=x.child.next;
						x.child.next=found;
						found.next.prev=found;
					} 
				 
					x.degree++;
					found.childcut=false;
					
					degreetable.remove(degree);
							
					degree++;
			 }
			 		 
			degreetable.put(degree, x);
			
			x=next;
			roots=roots-1;
		 } 
		 
	
		    maxnode=null;
		    for(Entry<Integer, node> entry : degreetable.entrySet()){
		    	found=entry.getValue();
			
		    	if (found == null) {
	                continue;
	            }
			 
		    	if(maxnode==null){
		    		maxnode=found;
		    	}
		    	else{
		    		//insert found into root list 
				 	found.prev.next=found.next;
				 	found.next.prev=found.prev;
				 
				 	found.prev = maxnode;
	                found.next = maxnode.next;
	                maxnode.next = found;
	                if(found.next!=null){
	                	 found.next.prev = found;
	                }              
	                
	                if(found.value>maxnode.value){
	                	maxnode=found;
	                	
	                }
				}
		   } 
		 
		
	}//end consolidate function

	public void increasekey(node val, int key) {
		// TODO Auto-generated method stub
		 //do nothing if the new key is smaller than old key
		 if(key<val.value){
			 
		 }
		 //else update key and check if it violates max heap property
		 else{
			 val.value=key;
			 node p=val.parent;
			
			 if(p!=null&& p.value<val.value){
				 cut(val,p);
				 cascadingcut(p);
			 }
		
			 if(val.value>maxnode.value){
				 	maxnode=val;
				 
			 }
			 
			 
		 }
		
	}//end function increase key


	private void cascadingcut(node p) {
		// TODO Auto-generated method stub
		//as from CLRS pseudo code
		 node z=p.parent;
		 if(z!=null){
			//if childcut of parent is false update to true 
			 if (p.childcut==false){
				 p.childcut=true;
			 }
			
			 else{
				 cut(p,z);
				 cascadingcut(z);
			 }
		 }
		 		
	}//end cascading cut function


	private void cut(node ptr, node p) {
		// TODO Auto-generated method stub
		 ptr.prev.next=ptr.next;
		 ptr.next.prev=ptr.prev;				//remove child from its siblings list
				  
		 if(p.child==ptr){
			 p.child=ptr.next;
		 }							//update child ptr if necessary
		 p.degree=p.degree-1;
		 		
		 if(p.degree==0){
		    p.child=null;
		 }
		
		 ptr.prev=maxnode;
	 	 ptr.next=maxnode.next;
	 	 maxnode.next=ptr;
	 	 if(ptr.next!=null){
	 		ptr.next.prev=ptr;
	 	 }							//add the child in top level list
	 	 
	 	ptr.parent=null;
	 	ptr.childcut=false;					//update parent and childcut values
	 	
		
	}//end function cut

}//end class
