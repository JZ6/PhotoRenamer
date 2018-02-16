/**
 * 
 */
package backend;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kamjha
 *
 */
public class TaggerTest {
	protected String item_1, item_2, item_3;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Initial values so that every test case 
		// can begin with them in this state
		item_1 = "won";
		item_2 = "2";
		item_3 = "three";
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		item_1 = null;
		item_2 = null;
		item_3 = null;
		Tagger t = new Tagger();
		String[] x = t.getAvailableTags().toArray(new String[t.getAvailableTags().size()]);
		for(String ta : x) {t.removetag(ta);}
		
	}

	/**
	 * Test method for {@link backend.Tagger#Tagger()}.
	 */
	@Test
	public void testTagger() {
		Tagger t = new Tagger();
		System.out.println(t.getAvailableTags());
		List<String> expected = new ArrayList<String>();
		List<String> actual = t.getAvailableTags();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link backend.Tagger#getAvailableTags()}.
	 */
	@Test
	public void testGetAvailableTagsEmpty() {
		Tagger t = new Tagger();
		List<String> actual = t.getAvailableTags();
		List<String> expected = new ArrayList<String>();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for {@link backend.Tagger#getAvailableTags()}.
	 */
	@Test
	public void testGetAvailableTagsNonEmpty() {
		Tagger t = new Tagger();
		t.addtag(item_1);
		t.addtag(item_2);
		t.addtag(item_3);
		List<String> expected = new ArrayList<String>();
		List<String> actual = t.getAvailableTags();
		expected.add(item_1);
		expected.add(item_2);
		expected.add(item_3);
		assertEquals(expected, actual);
		t = null;
	}

	/**
	 * Test method for {@link backend.Tagger#addtag(java.lang.String)}.
	 */
	@Test
	public void testAddtagSingle() {
		Tagger t = new Tagger();
		t.addtag(item_1);
		List<String> expected = new ArrayList<String>();
		expected.add(item_1);
		List<String> actual = t.getAvailableTags();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for {@link backend.Tagger#addtag(java.lang.String)}.
	 */
	@Test
	public void testAddtagMulti() {
		Tagger t = new Tagger();
		t.addtag(item_1);
		t.addtag(item_2);
		t.addtag(item_3);
		List<String> expected = new ArrayList<String>();
		expected.add(item_1);
		expected.add(item_2);
		expected.add(item_3);
		List<String> actual = t.getAvailableTags();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link backend.Tagger#removetag(java.lang.String)}.
	 */
	@Test
	public void testRemovetagSingle() {
		Tagger t = new Tagger();
		t.addtag(item_1);
		t.removetag(item_1);
		List<String> expected = new ArrayList<String>();
		List<String> actual = t.getAvailableTags();
		assertEquals(expected, actual);
		}
	
	/**
	 * Test method for {@link backend.Tagger#removetag(java.lang.String)}.
	 */
	@Test
	public void testRemovetagMulti() {
		Tagger t = new Tagger();
		t.addtag(item_1);
		t.addtag(item_3);
		t.addtag(item_2);
		t.removetag(item_1);
		t.removetag(item_3);
		t.removetag(item_2);
		List<String> expected = new ArrayList<String>();
		List<String> actual = t.getAvailableTags();
		assertEquals(expected, actual);
		}

}
