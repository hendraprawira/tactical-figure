package TacticalPoint;

import org.opensplice.dds.dcps.Utilities;

public final class TacticPointDataWriterHelper
{

    public static TacticPointDataWriter narrow(Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof TacticPointDataWriter) {
            return (TacticPointDataWriter)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

    public static TacticPointDataWriter unchecked_narrow(Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof TacticPointDataWriter) {
            return (TacticPointDataWriter)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

}
