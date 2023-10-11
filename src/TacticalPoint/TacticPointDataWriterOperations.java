package TacticalPoint;

public interface TacticPointDataWriterOperations extends
    DDS.DataWriterOperations
{

    long register_instance(
            TacticPoint instance_data);

    long register_instance_w_timestamp(
            TacticPoint instance_data,
            DDS.Time_t source_timestamp);

    int unregister_instance(
            TacticPoint instance_data,
            long handle);

    int unregister_instance_w_timestamp(
            TacticPoint instance_data,
            long handle, 
            DDS.Time_t source_timestamp);

    int write(
            TacticPoint instance_data,
            long handle);

    int write_w_timestamp(
            TacticPoint instance_data,
            long handle, 
            DDS.Time_t source_timestamp);

    int dispose(
            TacticPoint instance_data,
            long instance_handle);

    int dispose_w_timestamp(
            TacticPoint instance_data,
            long instance_handle, 
            DDS.Time_t source_timestamp);
    
    int writedispose(
            TacticPoint instance_data,
            long instance_handle);

    int writedispose_w_timestamp(
            TacticPoint instance_data,
            long instance_handle, 
            DDS.Time_t source_timestamp);

    int get_key_value(
            TacticPointHolder key_holder,
            long handle);
    
    long lookup_instance(
            TacticPoint instance_data);

}
