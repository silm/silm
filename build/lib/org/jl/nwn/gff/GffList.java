package org.jl.nwn.gff;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 */
public class GffList extends GffField implements Iterable<GffStruct>{
    
    private List<GffStruct> structs;
    
    public GffList( String label ){
        super( label, Gff.LIST );
        structs = new ArrayList<GffStruct>();
    }
    
    public GffList( List structs, String label ){
        this( label );
        this.structs = structs;
    }
    
    public GffStruct get( int pos ){
        return ( GffStruct ) structs.get( pos );
    }
    
    public void add( GffStruct s ){
        structs.add( s );
        s.parent = this;
    }
    
    public void add( int pos, GffStruct s ){
        //System.out.println( "adding to list " + label + ", pos " + pos + " : " + s.label );
        structs.add( pos, s );
        s.parent = this;
    }
    
    public void remove( GffStruct s ){
        structs.remove( s );
        s.parent = null;
    }
    
    public void remove( int pos ){
        ((GffField)structs.remove( pos )).parent = null;
    }
    
    public int getSize(){
        return structs.size();
    }
    
    /**
     * return an iterator over the structs contained in this list.
     * */
    public Iterator<GffStruct> iterator(){
        return structs.iterator();
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append( "----------------- [ begin list " + label + " ] -------------------\n" );
        for ( int i = 0; i < getSize(); i++ ){
            sb.append( get(i).toString() );
            sb.append("\n");
        }
        sb.append( "----------------- [ end of list " + label + " ] ------------------"   );
        return sb.toString();
    }
    
    /**
     * does a deep copy of this list
     * @return deep copy of this GffLIst
     * */
    public Object clone(){
        GffList clone = ( GffList ) super.clone();
        clone.structs = new ArrayList();
        for ( int i = 0; i < structs.size(); i++ ){
            clone.structs.add( (GffStruct) structs.get(i).clone() );
        }
        return clone;
    }
    
    public int indexOf(Object o) {
        return structs.indexOf(o);
    }
    
    
    public boolean allowsChildren(){
        return true;
    }
    
    public int getChildCount(){
        return getSize();
    }
    
    public GffField getChild( int index ){
        return get(index);
    }
    
    public int getChildIndex( GffField f ){
        return indexOf( f );
    }
    
    public void addChild( int index, GffField f ){
        add(index, (GffStruct) f);
    }
    
    public void removeChild( GffField f ){
        remove((GffStruct)f);
    }
    
    @Override public void setData( Object o ){} // no-op
    @Override public Object getData(){ return null; } // no-op
    
}