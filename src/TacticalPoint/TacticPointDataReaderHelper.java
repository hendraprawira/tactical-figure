package TacticalPoint;

import org.opensplice.dds.dcps.Utilities;

public final class TacticPointDataReaderHelper
{

    public static TacticPointDataReader narrow(Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof TacticPointDataReader) {
            return (TacticPointDataReader)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

    public static TacticPointDataReader unchecked_narrow(Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof TacticPointDataReader) {
            return (TacticPointDataReader)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

}
