//
//  File.swift
//  MagicHacks
//
//  Created by Marc Davis on 11/3/18.
//  Copyright © 2018 Marc Davis. All rights reserved.
//

import Foundation
import UIKit

enum CreatureType: Int, CaseIterable {
    case air = 0
    case earth = 1
    case electric = 2
    case fire = 3
    case plant = 4
    case water = 5
}

enum StatType {
    case A
    case B
    case C
}

enum AttributeType {
    case health
    case speed
    case attack
    case defense
    case magic
}

enum MoveType {
    
}

class Stats {    
    @IBOutlet var points: UILabel!
    @IBOutlet var statC: UILabel!
    @IBOutlet var statB: UILabel!
    @IBOutlet var statA: NSLayoutConstraint!
}

class Attributes {
    @IBOutlet var points: UILabel!
    @IBOutlet var hp: UILabel!
    @IBOutlet var speed: UILabel!    
    @IBOutlet var magic: UILabel!
    @IBOutlet var defense: UILabel!
    @IBOutlet var attack: UILabel!
}

class Moves {
    @IBOutlet var points: UILabel!
    @IBOutlet var moveValue: UILabel!
    @IBOutlet var moveName: UILabel!
    
}

class Creature {
    @IBOutlet var typeA: UILabel!
    @IBOutlet var typeB: UILabel!
    
    let types: [CreatureType]
    
    init(types: [CreatureType]) {
        guard types.count == 1 || types.count == 2 else {
            print("creatures must have one or two types")
            exit(0)
        }
        self.types = types
    }
    
    func image() -> UIImage {
        print("not yet implementeded")
        return UIImage(imageLiteralResourceName: "air")
    }
}
