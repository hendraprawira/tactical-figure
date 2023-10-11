package TacticalPoint;

import org.opensplice.dds.dcps.Utilities;

public final class TacticPointTypeSupportHelper
{

    public static TacticPointTypeSupport narrow(Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof TacticPointTypeSupport) {
            return (TacticPointTypeSupport)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

    public static TacticPointTypeSupport unchecked_narrow(Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof TacticPointTypeSupport) {
            return (TacticPointTypeSupport)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

}
