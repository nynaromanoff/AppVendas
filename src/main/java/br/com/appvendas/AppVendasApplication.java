package br.com.appvendas;


import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.appvendas.domain.Categoria;
import br.com.appvendas.domain.Cidade;
import br.com.appvendas.domain.Cliente;
import br.com.appvendas.domain.Endereco;
import br.com.appvendas.domain.Estado;
import br.com.appvendas.domain.ItemPedido;
import br.com.appvendas.domain.Pagamento;
import br.com.appvendas.domain.PagamentoComBoleto;
import br.com.appvendas.domain.PagamentoComCartao;
import br.com.appvendas.domain.Pedido;
import br.com.appvendas.domain.Produto;
import br.com.appvendas.domain.enums.EstadoPagamento;
import br.com.appvendas.domain.enums.TipoCliente;
import br.com.appvendas.repositories.CategoriaRepository;
import br.com.appvendas.repositories.CidadeRepository;
import br.com.appvendas.repositories.ClienteRepository;
import br.com.appvendas.repositories.EnderecoRepository;
import br.com.appvendas.repositories.EstadoRepository;
import br.com.appvendas.repositories.ItemPedidoRepository;
import br.com.appvendas.repositories.PagamentoRepository;
import br.com.appvendas.repositories.PedidoRepository;
import br.com.appvendas.repositories.ProdutoRepository;

@SpringBootApplication
public class AppVendasApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(AppVendasApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		Categoria cat1 = new Categoria (null, "Informática");
		Categoria cat2 = new Categoria (null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		Estado est3 = new Estado(null, "Bahia");

				
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		Cidade c4 = new Cidade(null, "Salvador", est3);
		

		Cliente cli1 = new Cliente(null, "Jessica Santos", "nynaalmeida@icloud.com", "04167009501", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("964775880", "24245858"));
		
		Endereco e1 = new Endereco(null, "Rua Odemis", "37", "Apto 41 Bloco 19", "Jardim Umuarama", "05783180", cli1, c2);
		Endereco e2 = new Endereco(null, "Avenida Margarida", "118", "casa do meio", "Cosme de Farias", "40253450", cli1, c4);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 =  new Pedido(null, sdf.parse("24/10/2017 14:05"), cli1, e1);
		Pedido ped2 =  new Pedido(null, sdf.parse("24/10/2017 14:15"), cli1, e1);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("27/10/2017 23:59"),null);
		ped2.setPagamento(pagto2);
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
								
		categoriaRepository.save(Arrays.asList(cat1, cat2));
		produtoRepository.save(Arrays.asList(p1, p2, p3));
		
		estadoRepository.save(Arrays.asList(est1, est2, est3));
		cidadeRepository.save(Arrays.asList(c1,c2,c3, c4));
		
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1, e2));
		
		pedidoRepository.save(Arrays.asList(ped1, ped2));
		pagamentoRepository.save(Arrays.asList(pagto1, pagto2));
		
		itemPedidoRepository.save(Arrays.asList(ip1, ip2, ip3));
	}
	
	
}
