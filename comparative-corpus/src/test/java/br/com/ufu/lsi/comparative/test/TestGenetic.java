package br.com.ufu.lsi.comparative.test;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import br.com.ufu.lsi.comparative.ga.engine.GeneticEngine;
import br.com.ufu.lsi.comparative.ga.model.Chromossome;

public class TestGenetic {

    @Test
    public void testChrom(){
        GeneticEngine engine = new GeneticEngine();
        
        Chromossome chromossome = new Chromossome();

        chromossome.setGenes( engine.buildRandomChromossome() );

        Chromossome chromossome2 = SerializationUtils.clone( chromossome );
        
        System.out.println( chromossome.equals( chromossome2 ));
    }
}
