package com.github.aureliano.verbum_domini.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegexHelperTest {

	@Test
	public void testScan() {
		String text = "[47] This man was innocent: or, \"This man was righteous.\"";
		String regex = "^\\[[^\\]]+\\]";
		
		assertEquals("[47]", RegexHelper.scan(regex, text));
		
		text = "[1-14] The particularly Lucan material in the travel narrative concludes with two parables on prayer. The first ( Luke 18:1-8) teaches the disciples the need of persistent prayer so that they not fall victims to apostasy ( Luke 18:8). The second ( Luke 18:9-14) condemns the self-righteous, critical attitude of the Pharisee and teaches that the fundamental attitude of the Christian disciple must be the recognition of sinfulness and complete dependence on God's graciousness. The second parable recalls the story of the pardoning of the sinful woman ( Luke 7:36-50) where a similar contrast is presented between the critical attitude of the Pharisee Simon and the love shown by the pardoned sinner.";
		assertEquals("[1-14]", RegexHelper.scan(regex, text));
		
		text = "[ 18:15- 19:27] Luke here includes much of the material about the journey to Jerusalem found in his Marcan source ( Luke 10:1-52) and adds to it the story of Zacchaeus ( Luke 19:1-10) from his own particular tradition and the parable of the gold coins (minas) ( Luke 19:11-27) from Q, the source common to Luke and Matthew";
		assertEquals("[ 18:15- 19:27]", RegexHelper.scan(regex, text));
	}
}