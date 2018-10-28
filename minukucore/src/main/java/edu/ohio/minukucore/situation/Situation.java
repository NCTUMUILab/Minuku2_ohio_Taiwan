/*
 * Copyright (c) 2016.
 *
 * DReflect and Minuku Libraries by Shriti Raj (shritir@umich.edu) and Neeraj Kumar(neerajk@uci.edu) is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/Shriti-UCI/Minuku-2.
 *
 *
 * You are free to (only if you meet the terms mentioned below) :
 *
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 *
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 *
 * Under the following terms:
 *
 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * NonCommercial — You may not use the material for commercial purposes.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 */

package edu.ohio.minukucore.situation;

import java.util.List;

import edu.ohio.minukucore.event.ActionEvent;
import edu.ohio.minukucore.event.MinukuEvent;
import edu.ohio.minukucore.exception.DataRecordTypeNotFound;
import edu.ohio.minukucore.model.DataRecord;
import edu.ohio.minukucore.model.StreamSnapshot;

/**
 * Created by shriti on 7/9/16.
 *
 * @author neerajkumar
 * A situation depends on certain type of data records
 * It generates an action events defined by the developer
 * The developer would code the rules for the situation in the
 * assertSituation method.
 */
public interface Situation {

    /**
     * Put the rules to be tested for the situation and return an action event
     * @param snapshot a data structure containing current and previous values of the stream when
     *                 the state change happened.
     *                 {@link edu.ohio.minukucore.model.StreamSnapshot}
     * @param <T>      the application specific DataRecord
     *                 {@link edu.ohio.minukucore.model.DataRecord}
     * @return         an {@link edu.ohio.minukucore.event.ActionEvent}
     *                 some {@link edu.ohio.minukucore.action.Action} will be
     *                 subscribed to this action event
     *
     */
    public <T extends ActionEvent> T assertSituation(StreamSnapshot snapshot,
                                                     MinukuEvent minukuEvent);

    /**
     * Fetch a list of DataRecords types that the situation depends on
     * @return the list of DataRecord classes
     * @throws {@link edu.ohio.minukucore.exception.DataRecordTypeNotFound}
     */
    public List<Class<? extends DataRecord>> dependsOnDataRecordType()
            throws DataRecordTypeNotFound;
}
