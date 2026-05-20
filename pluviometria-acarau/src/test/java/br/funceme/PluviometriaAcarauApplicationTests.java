package br.funceme;

import br.funceme.model.RegistroChuva;
import br.funceme.service.PluviometriaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes de PluviometriaService")
class PluviometriaServiceTest {

	@Autowired
	private PluviometriaService service;

	private static final int ANO = 2025;

	@Test
	@DisplayName("totalPorMes deve retornar 12 entradas")
	void testTotalPorMesRetorna12Meses() throws IOException {
		assertEquals(12, service.totalPorMes(ANO).size());
	}

	@Test
	@DisplayName("Todos os totais mensais devem ser >= 0")
	void testTotaisMensaisNaoNegativos() throws IOException {
		service.totalPorMes(ANO).values()
				.forEach(v -> assertTrue(v >= 0));
	}

	@Test
	@DisplayName("diaMaiorPrecipitacao deve estar presente")
	void testDiaMaiorPresente() throws IOException {
		Optional<RegistroChuva> maior = service.diaMaiorPrecipitacao(ANO);
		assertTrue(maior.isPresent());
		assertTrue(maior.get().getValor() > 0);
	}

	@Test
	@DisplayName("diaMenorPrecipitacao deve ser > 0 (exclui zeros)")
	void testDiaMenorPositivo() throws IOException {
		service.diaMenorPrecipitacao(ANO)
				.ifPresent(r -> assertTrue(r.getValor() > 0));
	}

	@Test
	@DisplayName("mesMaiorPrecipitacao deve estar entre 1 e 12")
	void testMesMaiorIntervalo() throws IOException {
		Optional<Integer> mes = service.mesMaiorPrecipitacao(ANO);
		assertTrue(mes.isPresent());
		assertTrue(mes.get() >= 1 && mes.get() <= 12);
	}

	@Test
	@DisplayName("mediaDiariaPorAno deve ser > 0")
	void testMediaAnualPositiva() throws IOException {
		assertTrue(service.mediaDiariaPorAno(ANO) > 0);
	}

	@Test
	@DisplayName("mediaDiariaPorMes deve retornar 12 entradas")
	void testMediaPorMes12Entradas() throws IOException {
		assertEquals(12, service.mediaDiariaPorMes(ANO).size());
	}

	@Test
	@DisplayName("topDias deve retornar exatamente 10 itens")
	void testTopDiasRetorna10() throws IOException {
		assertEquals(10, service.topDiasMaiorPrecipitacao(ANO, 10).size());
	}

	@Test
	@DisplayName("topDias deve estar em ordem decrescente")
	void testTopDiasOrdemDecrescente() throws IOException {
		List<RegistroChuva> top = service.topDiasMaiorPrecipitacao(ANO, 10);
		for (int i = 0; i < top.size() - 1; i++)
			assertTrue(top.get(i).getValor() >= top.get(i + 1).getValor());
	}

	@Test
	@DisplayName("Soma dos totais mensais deve igualar o total anual")
	void testSomaMensalIgualAnual() throws IOException {
		TreeMap<Integer, Double> totais = service.totalPorMes(ANO);
		double somaMensal = totais.values().stream().mapToDouble(Double::doubleValue).sum();
		assertEquals(service.totalAnual(ANO), somaMensal, 0.01);
	}
}