package TDAArbolBinario;
import java.util.Iterator;
import TDALista.*;
/**
 * Class ArbolBinario.
 * Implementa BinaryTree.
 *
 * @param <E> Tipo de los rótulos de los nodos.
 */
public class ArbolBinario<E> implements BinaryTree<E>{
    protected BTNode<E> root;
    protected int size;
    
    /**
     * Instancia un objeto de tipo ArbolBinario.
     */
    public ArbolBinario() {
    	root=null;
    	size=0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public Iterator<E> iterator(){
        PositionList<E> res= new SimplyLinkedList<E>();
        if(size>0)preorden(root,res);
        return res.iterator();
    }
    private void preorden(BTNode<E> u,PositionList<E>list){
        list.addLast(u.element());
        if(u.getLeft()!=null)preorden((BTNode<E>)u.getLeft(),list);
        if(u.getRight()!=null)preorden((BTNode<E>)u.getRight(),list);
    }

    public Iterable<Position<E>> positions(){
        PositionList<Position<E>> res = new SimplyLinkedList<Position<E>>();
        if(size>0)positions(root,res);
        return res;
    }
    private void positions(BTNode<E> u,PositionList<Position<E>>list){
        list.addLast(u);
        if(u.getLeft()!=null)positions((BTNode<E>)u.getLeft(),list);
        if(u.getRight()!=null)positions((BTNode<E>)u.getRight(),list);
    }

    protected BTNode<E> checkPosition(Position<E> p)throws InvalidPositionException{
        InvalidPositionException ex = new InvalidPositionException("p invalida");
        if(size==0 ||p==null)throw ex;
        try{
            return (BTNode<E>)p;
        }catch(ClassCastException e){
            throw ex;
        }
    }

    public E replace(Position<E> p,E e)throws InvalidPositionException{
        BTNode<E> u=checkPosition(p);
        E res = u.element();
        u.setElement(e);
        return res;
    }

    public Position<E> root()throws EmptyTreeException{
        if(size==0)throw new EmptyTreeException("Arbol vacio");
        return root;
    }

    public Position<E> parent(Position<E> p)throws InvalidPositionException, BoundaryViolationException{
        BTNode<E> u= checkPosition(p);
        if(u==root)throw new BoundaryViolationException("No hay padre de raiz");
        return u.getParent();
    }

    public Iterable<Position<E>> children(Position<E> p)throws InvalidPositionException{
        BTNode<E> u = checkPosition(p);
        PositionList<Position<E>> ch = new SimplyLinkedList<Position<E>>();
        if(u.getLeft()!=null)ch.addLast(u.getLeft());
        if(u.getRight()!=null)ch.addLast(u.getRight());
        return ch;
    }

    public boolean isInternal(Position<E> p)throws InvalidPositionException{
        BTNode<E> u = checkPosition(p);
        return u.getLeft()!=null || u.getRight()!=null;
    }

    public boolean isExternal(Position<E> p)throws InvalidPositionException{
        BTNode<E> u = checkPosition(p);
        return u.getLeft()==null && u.getRight()==null;
    }

    public boolean isRoot(Position<E> p)throws InvalidPositionException{
        BTNode<E> u=checkPosition(p);
        return u==root;
    }

    public Position<E> left(Position<E> p)throws InvalidPositionException, BoundaryViolationException{
        BTNode<E> u=checkPosition(p);
        if(u.getLeft()==null)throw new BoundaryViolationException("p no tiene left");
        return u.getLeft();
    }

    public Position<E> right(Position<E> p)throws InvalidPositionException, BoundaryViolationException{
        BTNode<E> u=checkPosition(p);
        if(u.getRight()==null)throw new BoundaryViolationException("p no tiene right");
        return u.getRight();
    }

    public boolean hasLeft(Position<E> p)throws InvalidPositionException{
        BTNode<E> u=checkPosition(p);
        return u.getLeft()!=null;
    }
    public boolean hasRight(Position<E> p)throws InvalidPositionException{
        BTNode<E> u=checkPosition(p);
        return u.getRight()!=null;
    }

    public Position<E> createRoot(E r)throws InvalidOperationException{
        if(size>0)throw new InvalidOperationException("arbol binario no vacio");
        root = new BTNode<E>(r);
        size=1;
        return root;
    }

    public Position<E> addLeft(Position<E> p,E r)throws InvalidPositionException, InvalidOperationException{
        BTNode<E> u= checkPosition(p);
        if(u.getLeft()!=null)throw new InvalidOperationException("p ya tiene left");
        u.setLeft(new BTNode<E>(r,u,null,null));
        size++;
        return u.getLeft();
    }

    public Position<E> addRight(Position<E> p,E r)throws InvalidPositionException, InvalidOperationException{
        BTNode<E> u= checkPosition(p);
        if(u.getRight()!=null)throw new InvalidOperationException("p ya tiene right");
        u.setRight(new BTNode<E>(r,u,null,null));
        size++;
        return u.getRight();
    }

    public E remove(Position<E> p)throws InvalidPositionException,InvalidOperationException{
        BTNode<E> u= checkPosition(p);
        if(u.getLeft()!=null && u.getRight()!=null)throw new InvalidOperationException("No se puede borrar un nodo con dos hijos");
        if(u==root){
            if(u.getLeft()==null&&u.getRight()==null){
                root=null;
            }
            else{
                if(u.getLeft()!=null){
                    root = (BTNode<E>)u.getLeft();
                    root.setParent(null);
                }
                else{
                    root = (BTNode<E>)u.getRight();
                    root.setParent(null);
                }
            }
        }
        else{
            BTNode<E> pu= (BTNode<E>)u.getParent();
            BTNode<E> h=null;
            if(u.getLeft()!=null)h=(BTNode<E>)u.getLeft();
            else if(u.getRight()!=null)h=(BTNode<E>)u.getRight();
            if(pu.getLeft()==u)pu.setLeft(h);
            else{
                if(pu.getRight()==u)pu.setRight(h);
                else throw new InvalidPositionException("padre de p, no conoce a p, arbol corrupto");
            }
            if(h!=null)h.setParent(pu);
        }
        E res = u.element();
        u.setElement(null);
        u.setLeft(null);
        u.setRight(null);
        u.setParent(null);
        size--;
        return res;
    }

    public void Attach(Position<E> p,BinaryTree<E>T1,BinaryTree<E> T2)throws InvalidPositionException{
        BTNode<E> u = checkPosition(p);
        if(u.getLeft()!=null||u.getRight()!=null)throw new InvalidPositionException("p no es hoja");

        try{    
            if(!T1.isEmpty()){
            	BTNode<E>left=new BTNode<E>(T1.root().element(),u,null,null);
                u.setLeft(left);
                size++;
                Attach(left,T1,T1.root());
            }
            if(!T2.isEmpty()){
            	BTNode<E>right=new BTNode<E>(T2.root().element(),u,null,null);
                u.setRight(right);
                size++;
                Attach(right,T2,T2.root());
            }
        }catch(EmptyTreeException e){
        	System.out.println(e.getMessage()+"\n"+e.getStackTrace());
        }
    }
    
    private void Attach(BTNode<E>u,BinaryTree<E>T,Position<E> p) {
    	try{    
            if(T.hasLeft(p)){
                u.setLeft(new BTNode<E>(T.left(p).element(),u,null,null));
                size++;
                Attach((BTNode<E>)u.getLeft(),T,T.left(p));
            }
            if(T.hasRight(p)){
                u.setRight(new BTNode<E>(T.right(p).element(),u,null,null));
                size++;
                Attach((BTNode<E>)u.getRight(),T,T.right(p));
            }
        }catch(InvalidPositionException | BoundaryViolationException e){
        	System.out.println(e.getMessage()+" en Attach");
        }
    }
    
}
