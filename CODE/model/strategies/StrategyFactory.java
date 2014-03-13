package model.strategies;

import java.util.HashMap;

/**
 * A factory that can get a particular strategy instance.
 * 
 * @author Daniel Hessellund Egeberg
 * @version 1
 */
public class StrategyFactory
{
    /**
     * Enum representing the various types of strategies.
     */
    public enum Type
    {
        RANDOM("Random", RandomStrategy.class),
        WALL_HUGGER("Wall-Hugger", WallHuggerStrategy.class);
        
        private String strategyName;
        private Class<? extends IStrategy> strategyClass;
        
        /**
         * Constructs a new strategy type.
         * 
         * @param   strategyName    the name of the strategy
         * @param   strategyClass   the corresponding class object
         */
        Type(String strategyName, Class<? extends IStrategy> strategyClass)
        {
            this.strategyName = strategyName;
            this.strategyClass    = strategyClass;
        }
        
        /**
         * Returns the name of the strategy.
         * 
         * @return strategy name
         */
        @Override
        public String toString()
        {
            return this.strategyName;
        }
    }

    /**
     * Private constructor to prevent instantiation
     */
    private StrategyFactory()
    {
        //This constructor is intentionally left blank.
    }
    
    /**
     * Gets an instance of a given strategy type.
     * 
     * @param   type    the type of the strategy
     * @return  the strategy
     */
    public static IStrategy getStrategy(Type type) throws InstantiationException, IllegalAccessException
    {
        return type.strategyClass.newInstance();
    }
    
    /**
     * Gets a map of the supported strategy types where the key is the name and the type as value.
     * 
     * @return the map of the supported strategy types
     */
    public static HashMap<String, Type> getStrategyTypes()
    {
        HashMap<String, Type> types = new HashMap<String, Type>();

        for (Type type : Type.values())
            types.put(type.strategyName, type);

        return types;
    }
}
