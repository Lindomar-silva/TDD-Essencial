import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CamelCaseConverterTest {

	private CamelCaseConverter camelCase;
	private String nome = "Lionel";

	@Before
	public void setup() {
		camelCase = new CamelCaseConverter();
	}

	@Test
	public void deveConverterNomeSimples() throws Exception {
		assertEquals(nome, camelCase.convert("lionel"));
	}

	@Test
	public void deveConverterNomeSimplesMisturadoMaisculoEMinusculo() throws Exception {
		assertEquals(nome, camelCase.convert("lIOnel"));
	}
	
}
