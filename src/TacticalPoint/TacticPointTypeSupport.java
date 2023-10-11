package TacticalPoint;

public class TacticPointTypeSupport extends org.opensplice.dds.dcps.TypeSupportImpl implements DDS.TypeSupportOperations
{
    private static final long serialVersionUID = 1L;

    private long copyCache;

    public TacticPointTypeSupport()
    {
        super("TacticalPoint::TacticPoint",
              "",
              "mockID",
              null,
              TacticPointMetaHolder.metaDescriptor);
    }

    @Override
    protected DDS.DataWriter create_datawriter ()
    {
        return new TacticPointDataWriterImpl(this);
    }

    @Override
    protected DDS.DataReader create_datareader ()
    {
        return new TacticPointDataReaderImpl(this);
    }

    @Override
    protected DDS.DataReaderView create_dataview ()
    {
        return new TacticPointDataReaderViewImpl(this);
    }
}
