
package br.com.ufu.lsi.comparative.ga.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import br.com.ufu.lsi.comparative.ga.model.CSRDataset;
import br.com.ufu.lsi.comparative.ga.model.Chromossome;
import br.com.ufu.lsi.comparative.ga.model.Gene;
import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.util.RandomGenerator;

public class GeneticEngine {

    private static final int POPULATION_SIZE = 50;

    private static final int GENERATIONS_NUMBER = 50;

    private static final Double CROSSOVER_RATE = 0.8;

    private static final Double MUTATION_RATE = 0.5;

    private static final Double REGULATION_FACTOR = 0.1;

    public List< Chromossome > generateInitialPopulation( List< Sentence > sentences ) {

        List< Chromossome > population = new ArrayList< Chromossome >();

        for ( int i = 0; i < POPULATION_SIZE; i++ ) {
            Chromossome chromossome = new Chromossome();

            chromossome.setGenes( buildRandomChromossome() );

            if ( ! checkChromossomeIsValid( chromossome ) ) {
                i-- ;
            } else if ( population.contains( chromossome ) ) {
                i-- ;
            } else {
                population.add( chromossome );
            }
        }

        calculatePopulationFitness( population, sentences );

        return population;
    }

    public void evolvePopulation( List< Chromossome > population, List< Sentence > sentences ) {

        for ( int i = 0; i < GENERATIONS_NUMBER; i++ ) {

            List< Chromossome > parentsSelected = selectParents( population );

            List< Chromossome > children = crossover( parentsSelected );

            mutation( children, MUTATION_RATE );

            //insertionAndRemoval( children, insertionRate, removalRate );

            calculatePopulationFitness( children, sentences );

            eliteSelection( population, children );

            System.out.println( "GENERATION ##" + i);
            GARunner.printPopulation( population );
        }
    }

    public void eliteSelection( List< Chromossome > population, List< Chromossome > children ) {

        for ( Chromossome child : children ) {
            if ( ! population.contains( child ) )
                population.add( child );
        }

        Collections.sort( population );

        population.subList( POPULATION_SIZE, population.size() ).clear();
    }

    public void calculatePopulationFitness( List< Chromossome > population,
            List< Sentence > sentences ) {

        for ( Chromossome chromossome : population ) {
            chromossome.setFitness( fitness( chromossome, sentences ) );
        }

    }

    public void mutation( List< Chromossome > children, Double mutationRate ) {

        Double quantity = mutationRate * children.size();
        int mutationNumber = quantity.intValue();

        List< Integer > mutationChromossomes = RandomGenerator.randListValues( 0, children.size() - 1, mutationNumber );

        for ( int i = 0; i < mutationChromossomes.size(); i++ ) {
            Integer index = mutationChromossomes.get( i );
            Chromossome tempChrom = mutate( children.get(index) );
            if( checkChromossomeIsValid( tempChrom ) && !children.contains( tempChrom ) ) {
                children.set( index, tempChrom );
            }
        }
    }

    public Chromossome mutate( Chromossome chromossome ) {
        
        Chromossome tempChrom = SerializationUtils.clone( chromossome );

        Gene[] genes = tempChrom.getGenes();

        for ( int j = 0; j < genes.length - 1; j++ ) {

            String value = genes[ j ].getValue();
            while ( value == genes[ j ].getValue() ) {
                value = CSRDataset.getPostagValue();
            }
            genes[ j ].setValue( value );
        }
        return tempChrom;

    }

    public List< Chromossome > crossover( List< Chromossome > parentsSelected ) {

        List< Chromossome > children = new ArrayList< Chromossome >();

        for ( int i = 0; i < parentsSelected.size() - 1; i += 2 ) {
            Chromossome c1 = parentsSelected.get( i );
            Chromossome c2 = parentsSelected.get( i + 1 );
            Chromossome[] twoChildren = twoPointCrossover( c1, c2 );

            if ( ! children.contains( twoChildren[ 0 ] ) && ! children.contains( twoChildren[ 1 ] )
                    && checkChromossomeIsValid( twoChildren[ 0 ] )
                    && checkChromossomeIsValid( twoChildren[ 1 ] ) ) {

                children.add( twoChildren[ 0 ] );
                children.add( twoChildren[ 1 ] );
            } else {
                i -= 2;
            }
        }

        return children;
    }

    public Chromossome[] twoPointCrossover( Chromossome c1, Chromossome c2 ) {

        Chromossome child1 = SerializationUtils.clone( c1 );
        Chromossome child2 = SerializationUtils.clone( c2 );

        int firstPoint = RandomGenerator.randInt( 0, child1.getGenes().length - 1 );
        int secondPoint = firstPoint;
        while ( secondPoint == firstPoint ) {
            secondPoint = RandomGenerator.randInt( 0, child1.getGenes().length - 1 );
        }

        for ( int i = firstPoint + 1; i <= secondPoint; i++ ) {
            Gene[] child1genes = child1.getGenes();
            Gene[] child2genes = child2.getGenes();
            String tempValue = child1genes[ i ].getValue();
            child1genes[ i ].setValue( child2genes[ i ].getValue() );
            child2genes[ i ].setValue( tempValue );
        }

        Chromossome[] twoChildren = {
                child1, child2
        };

        return twoChildren;
    }

    public Double fitness( Chromossome chromossome, List< Sentence > sentences ) {

        Double antecedent = 0.0;
        Double satisfy = 0.0;

        for ( Sentence sentence : sentences ) {

            if ( antecedent( chromossome, sentence ) ) {
                antecedent++ ;
                if ( consequent( chromossome, sentence ) ) {
                    satisfy++ ;
                }
            }
        }

        Double fitness = antecedent != 0.0 ? satisfy / antecedent + REGULATION_FACTOR
                : REGULATION_FACTOR;

        return fitness;
    }

    public boolean antecedent( Chromossome chromossome, Sentence sentence ) {

        List< String > tags = new ArrayList< String >();
        for ( Gene gene : chromossome.getGenes() ) {
            String value = gene.getValue();
            if ( value.charAt( 0 ) == '1' ) {
                tags.add( value.substring( 1 ) );
            }
        }

        int i = 0;
        int asserts = 0;
        for ( String tag : tags ) {

            for ( ; i < sentence.getTaggedWords().size(); i++ ) {
                String sentenceTag = sentence.getTaggedWords().get( i ).tag();
                if ( tag.equals( sentenceTag ) ) {
                    asserts++ ;
                    break;
                }
            }
        }

        return asserts == tags.size() ? true : false;
    }

    public boolean consequent( Chromossome chromossome, Sentence sentence ) {

        String geneClass = chromossome.getGenes()[ chromossome.getGenes().length - 1 ].getValue();

        return sentence.getCategory().equals( geneClass );
    }

    public List< Chromossome > selectParents( List< Chromossome > population ) {

        List< Chromossome > populationClone = new ArrayList< Chromossome >( population );

        Double quantity = Math.ceil( CROSSOVER_RATE * population.size() );
        int parentsNumber = quantity.intValue();
        parentsNumber = parentsNumber % 2 == 0 ? parentsNumber : parentsNumber + 1;

        List< Chromossome > parents = new ArrayList< Chromossome >();
        while ( parents.size() < parentsNumber ) {

            Chromossome[] selectedParents = rouletteWheelSelection( populationClone );
            parents.add( selectedParents[ 0 ] );
            parents.add( selectedParents[ 1 ] );

            populationClone.remove( selectedParents[ 0 ] );
            populationClone.remove( selectedParents[ 1 ] );
        }

        populationClone = null;

        return parents;
    }

    public Chromossome[] rouletteWheelSelection( List< Chromossome > population ) {

        List< Chromossome > roulette = new ArrayList< Chromossome >();
        double totalSum = 0.0;

        for ( Chromossome chromossome : population ) {
            totalSum += chromossome.getFitness();
        }

        for ( Chromossome chromossome : population ) {
            double fitness = chromossome.getFitness();
            double proportion = fitness / totalSum;
            double quantity = Math.ceil( proportion * 100 );
            for ( int j = 0; j < quantity; j++ ) {
                roulette.add( chromossome );
            }
        }

        int parentId1 = RandomGenerator.randInt( 0, roulette.size() - 1 );
        int parentId2 = parentId1;
        while ( parentId2 == parentId1 )
            parentId2 = RandomGenerator.randInt( 0, roulette.size() - 1 );

        Chromossome[] parents = {
                roulette.get( parentId1 ), roulette.get( parentId2 )
        };

        return parents;
    }

    public Gene[] buildRandomChromossome() {

        Gene[] genes = new Gene[ CSRDataset.ATTRIBUTES_NUMBER ];

        for ( int i = 0; i < genes.length - 1; i++ ) {
            Gene gene = new Gene();
            gene.setValue( CSRDataset.getPostagValue() );
            gene.setIsClass( false );
            genes[ i ] = gene;
        }
        Gene classGene = new Gene();
        classGene.setIsClass( true );
        classGene.setValue( CSRDataset.getClassValue() );
        genes[ genes.length - 1 ] = classGene;

        return genes;
    }

    public boolean checkChromossomeIsValid( Chromossome chromossome ) {

        for ( Gene gene : chromossome.getGenes() ) {
            if ( gene.getValue().charAt( 0 ) == '1' )
                return true;
        }
        return false;
    }

}
