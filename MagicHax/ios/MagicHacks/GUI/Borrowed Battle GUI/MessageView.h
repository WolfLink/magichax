//
//  MessageView.h
//  PokeWatch
//
//  Created by Marc Davis on 4/10/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BattleMessenger.h"

@interface MessageView : UIView <MessengerDisplay> {
    UILabel *myLabel;
    BOOL nowPlaying;
}

@end
