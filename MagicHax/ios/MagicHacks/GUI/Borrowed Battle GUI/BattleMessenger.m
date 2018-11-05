//
//  BattleMessenger.m
//  PokeWatch
//
//  Created by Marc Davis on 4/6/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import "BattleMessenger.h"

static NSMutableArray *queue;
static double delay;
static __weak id<MessengerDisplay> display;
@implementation BattleMessenger
+(void)initialize {
    delay = 1;
    queue = [[NSMutableArray alloc] init];
}
+(void)addMessage:(NSString *)message {
    [queue addObject:message];
    [display displayMessages];
}
+(NSString *)getNextMessage {
    NSString *message = [queue objectAtIndex:0];
    [queue removeObjectAtIndex:0];
    return message;
}
+(BOOL)isEmpty {
    return queue.count == 0;
}
+(void)setDisplay:(id<MessengerDisplay>)disp {
    display = disp;
}
+(void)setDelay:(double)d {
    delay = d;
}
+(double)getDelay {
    if (delay == 0) {
        delay =1;
    }
    return delay;
}
@end
