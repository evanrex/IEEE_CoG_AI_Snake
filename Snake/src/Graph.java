import java.util.*;

public class Graph{
	
	//ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	ArrayList<ArrayList<Vertex>> board = new ArrayList<ArrayList<Vertex>>();
	ArrayList<Vertex> snake;
	int graphOrder; 
	Vertex apple;
	
	public Graph(int order) {
		this.graphOrder = order;
		
		for(int rows = 0; rows<50; rows++) {
        	ArrayList<Vertex> row = new ArrayList<Vertex>();
        	for(int cols = 0; cols<50; cols++) {
        		Vertex v = new Vertex(50*rows + cols);
        		v.x = cols;
        		v.y = rows;       
        		row.add(v);
        	}
        	this.board.add(row);
        }
		
		
		
			
		
		
	}
	
	public void resetBoardParents() {
		for(int i = 0; i<50; i++) {
			for(int j = 0; j<50; j++) {
				board.get(j).get(i).parent = null;
			}
		}
	}
	public int countParents() {
		int count = 0;
		for(int i = 0; i<50; i++) {
			for(int j = 0; j<50; j++) {
				if(board.get(j).get(i).parent != null) {
					count++;
				}
				
			}
		}
		
		return count;
	}
	
	public void makeEdges() {
		for(int row = 0; row<50; row++) {
        	for(int col = 0; col<50; col++) {
        		Vertex v = board.get(row).get(col);
        		
        		if(row-1 >= 0) { //up 
        			
        			Vertex w = board.get(row-1).get(col);
        			if(w.type == -1 || w.type == 15 || w.type == 100) {
        				v.addAdjacency(w);
        			}
        			
        		}
        		
        		if(row+1 < 50) { //down 
        			Vertex w = board.get(row+1).get(col);
        			if(w.type == -1 || w.type == 15 || w.type == 100) {
        				v.addAdjacency(w);
        			}
        		}
        		
        		if(col-1 >= 0) { //left
        			Vertex w = board.get(row).get(col-1);
        			if(w.type == -1 || w.type == 15 || w.type == 100) {
        				v.addAdjacency(w);
        			}
        		}
        		
        		if(col+1 < 50) { //right
        			Vertex w = board.get(row).get(col+1);
        			if(w.type == -1 || w.type == 15 || w.type == 100) {
        				v.addAdjacency(w);
        			}
        		}
        	}
        }
	}
	
	
	
	public void dfs(Vertex node){
		
		//System.out.print(node.data + " ");
		
        node.visited=true;
        for(Vertex n : node.adjacencies) {
        	if(n!=null && !n.visited && n.type == -1){
				dfs(n);
			}
        }
	}
	
	public void bfs(Vertex v) {
		ArrayList<Vertex> list = new ArrayList<Vertex>();
		
		list.add(v);
		v.visited = true;
		
		while(!list.isEmpty()) {
			Vertex w = list.get(0);
			list.remove(0);
			for(Vertex n : w.adjacencies) {
				if(!n.visited && n.type == -1 && n.type != 10) {
					n.visited = true;
					n.parent = w;
					w.children.add(n);
					list.add(w);
				}	
			}
		}
		
	}
	
	public Vertex deepestNode(Vertex root, int level) {
		
		Vertex deepestNode = root;
		int deepestLevel = level;
		
		if (root != null) {
			
			for(int i = 0; i< root.children.size()-1; i++) {
				
				deepestNode(root.children.get(i), ++level);
				if (level > deepestLevel) {
					deepestNode = root.children.get(i);
					deepestLevel = level;
				}
				
				deepestNode(root.children.get(i+1), level);
				
			}
			
		}
		
		return deepestNode;
		
	}
	
	public void greyness(Vertex start, int greyness) {
		
		start.greyness = 0;
		
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(20, 
                new Comparator<Vertex>(){
                         //override compare method
         public int compare(Vertex i, Vertex j){
        	 int igreyness = 0;
        	 int jgreyness = 0;
        	 
        	for(Vertex greyAdj : i.adjacencies) {
        		if(greyAdj.greyness == -1) {
        			igreyness++;
        		}
        	}
        	
        	for(Vertex greyAdj : j.adjacencies) {
        		if(greyAdj.greyness == -1) {
        			jgreyness++;
        		}
        	}
        	
        	if(igreyness > jgreyness) {
        		return 1;
        	}
        	
        	else if(jgreyness > igreyness) {
        		return -1;
        	}
        	 
        	else {
        		return 1;
        	}
         }

        });
		
		
		for(Vertex adj : start.adjacencies) {
			queue.add(adj);
			adj.greyness = start.greyness + 1;
		}
		
		
	}

	

}
