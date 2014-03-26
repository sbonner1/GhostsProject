package edu.ycp.cs496.ghosts.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GhostTest {
	private BlueGhost blueGhost;
	private RareGhost_001 rareGhost_001;
	private RedGhost redGhost;
	private YellowGhost yellowGhost;
	
	@Before
	public void setUp() {
		blueGhost = new BlueGhost();
		rareGhost_001 = new RareGhost_001();
		redGhost = new RedGhost();
		yellowGhost = new YellowGhost();
	}
	
	@Test
	public void testGhost() {
		// TODO: test
	}
}
