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

package edu.ohio.minukucore.event;

/**
 * The EventBus will be an object-agnostic event bus and must use a POJO as an Event type
 * and must allow any type of class to subscribe to any kind of event.
 *
 * This is inspired by the EventBus design by GreenRobot.
 * @see <a href="https://github.com/greenrobot/EventBus/tree/master/EventBus/src/org/greenrobot/eventbus">
 *     Git Repo for Green Robot Event Bus
 *     </a>
 * Created by Neeraj Kumar on 7/12/2016.
 */
public interface EventBus {

    /**
     * Registers an object as a subscriber with the EventBus. The subscriber is responsible for
     * using {@link Subscribe subscribe} annotation with one of the methods within itself as a
     * handler for one of the events which the subscriber is interested in.
     * @param subscriber
     */
    public void register(Object subscriber);

    /**
     * Unregisters an object as a subscriber. This will remove all subscriptions that the object
     * did using the {@link Subscribe subscribe} annotation for the handlers within the object.
     * @param subscriber
     */
    public void unregister(Object subscriber);

}
