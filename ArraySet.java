import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * ArraySet.java.
 *
 * Provides an implementation of the Set interface using an
 * array as the underlying data structure. Values in the array
 * are kept in ascending natural order and, where possible,
 * methods take advantage of this. Many of the methods with parameters
 * of type ArraySet are specifically designed to take advantage
 * of the ordered array implementation.
 *
 * @author Brian Betz (betzbri@auburn.edu)
 * @author	Dean Hendrix (dh@auburn.edu)
 * @version	2016-03-01
 * @param <T>
 *
 */
public class ArraySet<T extends Comparable<? super T>> implements Set<T> {

   ////////////////////////////////////////////
   // DO NOT CHANGE THE FOLLOWING TWO FIELDS //
   ////////////////////////////////////////////
   T[] elements;
   int size;

   ////////////////////////////////////
   // DO NOT CHANGE THIS CONSTRUCTOR //
   ////////////////////////////////////
   /**
    * Instantiates an empty set.
    */
   @SuppressWarnings("unchecked") 
   public ArraySet() {
      elements = (T[]) new Comparable[1];
      size = 0;
   }
   

   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements,
    *               false otherwise.
    */
   public boolean isEmpty() {
      return size == 0;
   }

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this ArraySet.
    *
    * @return a string representation of this ArraySet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }

   /**
    * Ensures the collection contains the specified element.
    * No specific order can be assumed. Neither duplicate nor null
    * values are allowed.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
    
    
   public boolean add(T element) {
   
      
      if (isFull()) {
         resizeArray(elements.length * 2);
      }
      
      if (element != null && !contains(element)) {
         if (size == 0) {
            elements[size] = element;
            size++;
            return true;
         }
      }
      
      if (element != null && !contains(element)) {
      
         for (int i = 0; i <= size; i++) {
            if (element.compareTo(elements[size - 1]) > 0) {
               elements[size] = element;
               size++;
               return true; 
            }
         }
      }
      
      if (element != null && !contains(element)) {
      
         for (int i = 0; i < size; i++) {
            if (element.compareTo(elements[i]) < 0) {
               System.arraycopy(elements, i, elements, (i + 1), (size - i));
               elements[i] = element;
               size++;
               return true;
            }
         }
      }
      
      return false;
   }
        
   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
   
      int index = locate(element);
   
      if (index >= size || index == -1) {
         return false;
      }
   
      for (int z = index; z < size; z++) {
         elements[z] = elements[z + 1];
      }
      
      size--;
   
   
      if (size > 0 && size < (elements.length / 4)) {
         resizeArray(elements.length / 2);
      }
   
      return true;
   
   }
   

   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection
    *                   is to be tested.
    * @return  true if this collection contains the specified element,
    *               false otherwise.
    */
   public boolean contains(T element) {
      if (this.locate(element) == -1) {
         return false;
      }
      
      return true;
   }

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    * @param s is parameter
    */
    
    
   public boolean equals(Set<T> s) { 
      if (s.size() != this.size) {
         return false;
      }
   
      for (int i = 0; i < this.size(); i++) {
         if (s.contains(elements[i])) {
            return true;
         }
      }
   
      return false;
   }

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    * @param s is parameter
    */
   public boolean equals(ArraySet<T> s) {
      if (s.size() != this.size) {
         return false;
      }
      for (int z = 0; z < this.size; z++) {
         if (s.elements[z].compareTo(this.elements[z]) != 0) {
            return false;
         }
      }
   
      return true;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    * @param s is parameter
    */
   public Set<T> union(Set<T> s) {
      ArraySet<T> arraySet = new ArraySet<T>();
      
      for (T obj : this) {
         arraySet.add(obj);
      }
   
      if (s.isEmpty()) {
         return arraySet;
      }
      
      for (T obj : s) {
         arraySet.add(obj);
      }
   
      return arraySet;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    * @param s is parameter
    */
   public Set<T> union(ArraySet<T> s) {
      ArraySet<T> arraySet1 = new ArraySet<T>();
      for (T obj : this) {
         arraySet1.add(obj);
      }
   
      if (s.isEmpty()) {
         return arraySet1;
      }
   
      for (T obj1 : s) {
         arraySet1.add(obj1);
      }
      
      return arraySet1;
   }


   /**
    * Returns a set that is the intersection of this set
    * and the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    * @param s is parameter
    */
   public Set<T> intersection(Set<T> s) {
      ArraySet<T> arraySet = new ArraySet<T>();
      for (T obj : this) {
         if (s.contains(obj)) {
            arraySet.add(obj);
         }
      }
      return arraySet;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    * @param s is parameter
    */
    
   public Set<T> intersection(ArraySet<T> s) {
      ArraySet<T> arraySet = new ArraySet<T>();
      
      if (!this.isEmpty()) {
         for (T obj : this) {
            if (s.contains(obj)) {
               arraySet.add(obj);
            }
         }
      }
      
      return arraySet;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    * @param s is parameter
    */
   public Set<T> complement(Set<T> s) {
   
      if (s == null || s.isEmpty()) {
         return this;
      }
      
      if (this == null || isEmpty()) {
         return this;
      }
      
      ArraySet<T> aSet = new ArraySet<T>();
      
      for (T obj : this) {
         if (!s.contains(obj)) {
            aSet.add(obj);
         }
      }
      
      return aSet;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    * @param s is parameter
    */
    
   @SuppressWarnings("unchecked")
   public Set<T> complement(ArraySet<T> s) {
      if (s == null || s.isEmpty()) {
         return this;
      }
      
      if (this == null || isEmpty()) {
         return this;
      }
      
      ArraySet<T> aSet = new ArraySet<T>();
      
      for (T obj : this) {
         if (!s.contains(obj)) {
            aSet.add(obj);
         }
      }
      
      return aSet;
   }


   /**
    * Returns an iterator over the elements in this ArraySet.
    * No specific order can be assumed.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   public Iterator<T> iterator() {
      return new ArraySetIterator(elements, size);
   }

   /**
    * Returns an iterator over the elements in this ArraySet.
    * The elements are returned in descending order.
    *
    * @return  an iterator over the elements in this ArraySet
    */
   public Iterator<T> descendingIterator() {
      return new DescIterator(elements, size);
   }

   /**
    * Returns an iterator over the members of the power set
    * of this ArraySet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return new PowerSetIterator(elements, size);
   }
   
   /**
   * This class creates an iterator which is used to iterate over 
   * the elements in the Iterator method.
   */
       
   private class ArraySetIterator implements Iterator<T> {
      private T[] objects;
      private int count;
      private int current;
   
      public ArraySetIterator(T[] elements, int size) {
         objects = elements;
         count = size;
         current = 0;
      }
      
      @Override
      public boolean hasNext() {
         return (current < count); 
      }
      
      @Override
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         else {
            return objects[current++];
         }
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
   
   /**
   * This class creates an iterator which is used to iterate over 
   * the elements in the descendingIterator method.
   */
   
   private class DescIterator implements Iterator<T> {
      private T[] objects;
      private int count;
      private int current;
   
      public DescIterator(T[] elements, int size) {
         objects = elements;
         count = 0;
         current = size - 1;
      }
   
      @Override
      public boolean hasNext() {
         return (current >= count);
      }
   
      @Override
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
      
         return objects[current--];
      }
   
      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
   
   
   /**
   * This class creates a PowerSetIterator.
   */
   
   private class PowerSetIterator implements Iterator<Set<T>> {
      private T[] objects;
      private int count;
      private int current;
      private int bit;
   
      public PowerSetIterator(T[] elements, int size) {
         objects = elements;
         count = size;
         current = 0;
         bit = 0;
      }
   
      public boolean hasNext() {
         return (current < (int) Math.pow(2, size));
      }
   
      public Set<T> next() {
         Set<T> set = new ArraySet<T>();
         int m = 1;
         for (int i = 0; i < size; i++) {
            if ((bit & m) != 0) {
               set.add(elements[i]);
            }
         }
         current++;
         bit = 0;
         return set;
      }
   
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
   
   
   
   private int locate(T element) {
      int lo = 0;
      int hi = size - 1;
      int mid = 0;
      while (lo <= hi && elements != null) {
         mid = (lo + hi) / 2;
         if (element.compareTo(elements[mid]) < 0) {
            hi = mid - 1;
         }
         else if (element.compareTo(elements[mid]) > 0) {
            lo = mid + 1;
         }
         
         else if (element.compareTo(elements[mid]) == 0) {
            return mid;
         }
      }
   
      return -1;
   }
   
   
   @SuppressWarnings("unchecked")
   
   private void resizeArray(int capacity) {
      T[] temp = (T[]) new Comparable[capacity];
      Iterator<T> itr = this.iterator();
      int i = 0;
      while (itr.hasNext()) {
         temp[i] = itr.next();
         i++;
      }
      elements = temp;
   }
      
   
   
   private boolean isFull() {
      if (size == elements.length) {
         return true;
      }
      return false;
   }
}
