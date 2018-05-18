package src.input.algo;

import org.apache.commons.lang3.StringUtils;
import src.input.map.Map;

public class SimpleHeruisticFunction implements IHeruisticFunction {

    @Override
    public int calculateGCoast(Map state)
    {
        String internal = state.getInternal();
        String finalState = Map.getFinalState().getInternal();

        return StringUtils.difference(internal, finalState).length();
    }
}
