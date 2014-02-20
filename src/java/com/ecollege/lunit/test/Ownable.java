/**
 * 
 */
package com.ecollege.lunit.test;

/**
 * @author toddf
 * @since September 29, 2009
 */
public interface Ownable<T>
{
	public T getOwner();
	public boolean hasOwner();
	public void setOwner(T owner);
}
