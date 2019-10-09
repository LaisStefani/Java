package mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.hbase.client.*;

public class Main {
    public static void main(String[] args) throws Exception {

        //configuração do hbase
        Configuration conf = HBaseConfiguration.create();

        //Uma instancia do job passando a configuração do hbase
        Job job = Job.getInstance(conf);
        //JOB: precisa saber q qualquer arquivo java contem a instancia dessa classe
        job.setJarByClass(Main.class);

        //especificamos a tabela de origem e destino
        String sourceTable = "census";
        String targetTable = "summary";

        Scan scan = new Scan();        // operação de varredura
        scan.setCaching(500);         // 1 é o padrão no Scan, o que será ruim para os trabalhos do MapReduce
        scan.setCacheBlocks(false);  // não definido como true para tarefas de MR

        //informamos qual o tipo que ele depende, basicamente extrirá todas as dependecias e
        //disponibilizará para o hadoop para que ele sai=ba aonde vai ser incluida cada coisa
        TableMapReduceUtil.addDependencyJars(job);

        //Mapeamento: vai pegar os parametros, inicializar um trabalho de mapeamento pra vc
        TableMapReduceUtil.initTableMapperJob(
                sourceTable,                     // tabela de entrada
                scan,                           // Instância de varredura para controlar CF e seleção de atributo
                MaritalStatusMapper.class,     // classe mapper
                ImmutableBytesWritable.class, // chave de saída do mapeador
                IntWritable.class,           // valor da saída do mapeador
                job);                       //instancia o job que será exxecutado

        //Redução: passamos tres pacotes de informaçção
        TableMapReduceUtil.initTableReducerJob(
                targetTable,                    // tabela de saída
                MaritalStatusReducer.class,    // classe redutora
                job);                         //instancia o job que será exxecutado
        job.setNumReduceTasks(1);            // pelo menos um, ajuste conforme necessário

        //enviamos o trabalho para o cluster do hadoop chamando job
        job.waitForCompletion(true);
        //Para isso vamos empacotar o codico em um arquivo jar
    }

}