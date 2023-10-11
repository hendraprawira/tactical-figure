package TacticalPoint;

import org.opensplice.dds.dcps.Utilities;

public final class TacticPointDataReaderViewHelper
{

    public static TacticPointDataReaderView narrow(Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof TacticPointDataReaderView) {
            return (TacticPointDataReaderView)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

    public static TacticPointDataReaderView unchecked_narrow(Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof TacticPointDataReaderView) {
            return (TacticPointDataReaderView)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

}
