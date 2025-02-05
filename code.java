import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    protected String nome;
    protected LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void aumentarSalario(BigDecimal percentual) {
        this.salario = this.salario.add(this.salario.multiply(percentual));
    }

    public String getFuncao() {
        return funcao;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("Nome: %s, Data de Nascimento: %s, Salário: %,.2f, Função: %s",
                nome, dataNascimento.format(formatter), salario, funcao);
    }
}

public class Principal{
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 8, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        ///Remover João
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        /// Imprimir todos os funcionários
        funcionarios.forEach(System.out::println);

        ///Aumentar salário em 10%
        funcionarios.forEach(f -> f.aumentarSalario(new BigDecimal("0.10")));

        /// Agrupar funcionários por função
        Map<String, List<Funcionario>> agrupadosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        /// Imprimir funcionários agrupados por função
        agrupadosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(System.out::println);
        });

        /// Filtrar aniversariantes de outubro e dezembro
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(System.out::println);

        /// Funcionário mais velho
        Funcionario maisVelho = Collections.max(funcionarios, Comparator.comparing(f -> Period.between(f.getDataNascimento(), LocalDate.now()).getYears()));
        System.out.println("\nFuncionário mais velho: " + maisVelho.getNome() + ", Idade: " + Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears());

        /// Funcionários por ordem alfabética
        System.out.println("\nFuncionários ordenados alfabeticamente:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);

        /// Total dos salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários: " + String.format("%,.2f", totalSalarios));

        /// Quantos salários mínimos ganha cada funcionário (considerando R$1212.00)
        System.out.println("\nSalários em relação ao mínimo de R$1212,00:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal qtdSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + " ganha " + qtdSalariosMinimos + " salários mínimos.");
        });
    }
}
